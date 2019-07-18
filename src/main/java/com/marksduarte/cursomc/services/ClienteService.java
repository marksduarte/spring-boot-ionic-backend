package com.marksduarte.cursomc.services;

import com.marksduarte.cursomc.domain.Cidade;
import com.marksduarte.cursomc.domain.Cliente;
import com.marksduarte.cursomc.domain.Endereco;
import com.marksduarte.cursomc.domain.enums.Perfil;
import com.marksduarte.cursomc.domain.enums.TipoCliente;
import com.marksduarte.cursomc.dto.ClienteDTO;
import com.marksduarte.cursomc.dto.ClienteNewDTO;
import com.marksduarte.cursomc.repositories.ClienteRepository;
import com.marksduarte.cursomc.repositories.EnderecoRepository;
import com.marksduarte.cursomc.security.UserSS;
import com.marksduarte.cursomc.services.exceptions.AuthorizationException;
import com.marksduarte.cursomc.services.exceptions.DataIntegrityException;
import com.marksduarte.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEnconder;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile")
	private String prefix;

	public Cliente find(final Integer id) {
				
		UserSS user = UserService.authenticated();
		System.out.println(user.getId());
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(final Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos.");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente findByEmail(final String email) {

		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado!");
		}
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null)
			throw new ObjectNotFoundException("Cliente não encontrado! Id: " + user.getId()
					+ ", Tipo: " + Cliente.class.getName());

		return cliente;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(),
				objDto.getCpfOuCnpj(), passwordEnconder.encode(objDto.getSenha()) , TipoCliente.toEnum(objDto.getTipo()));
		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(),
				objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cliente, cidade);

		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2()!=null)
			cliente.getTelefones().add(objDto.getTelefone2());
		if(objDto.getTelefone3()!=null)
			cliente.getTelefones().add(objDto.getTelefone3());
		return cliente;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if(user == null) throw new AuthorizationException("Acesso negado");

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
