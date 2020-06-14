package io.github.marksduarte.api.services.validation;

import io.github.marksduarte.api.domain.Cliente;
import io.github.marksduarte.api.domain.enums.TipoCliente;
import io.github.marksduarte.api.dto.ClienteNewDTO;
import io.github.marksduarte.api.repositories.ClienteRepository;
import io.github.marksduarte.api.resources.exceptions.FieldMessage;
import io.github.marksduarte.api.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo() == null)
            list.add(new FieldMessage("Tipo", "Tipo não pode ser nulo"));

        // incluir testes aqui
        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()))
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj()))
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ invalido"));

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if(aux != null)
            list.add(new FieldMessage("email", "Email já cadastrado."));

        for(FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();

    }
}
