package com.marksduarte.cursomc.services.validation;

import com.marksduarte.cursomc.domain.Cliente;
import com.marksduarte.cursomc.domain.enums.TipoCliente;
import com.marksduarte.cursomc.dto.ClienteDTO;
import com.marksduarte.cursomc.dto.ClienteNewDTO;
import com.marksduarte.cursomc.repositories.ClienteRepository;
import com.marksduarte.cursomc.resources.exceptions.FieldMessage;
import com.marksduarte.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        Map<String, String> mapRequest =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Integer idUri = Integer.parseInt(mapRequest.get("id"));
        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(idUri))
            list.add(new FieldMessage("email", "Email já cadastrado."));

        for(FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();

    }
}
