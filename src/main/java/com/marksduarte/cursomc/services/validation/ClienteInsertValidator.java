package com.marksduarte.cursomc.services.validation;

import com.marksduarte.cursomc.domain.enums.TipoCliente;
import com.marksduarte.cursomc.dto.ClienteNewDTO;
import com.marksduarte.cursomc.resources.exceptions.FieldMessage;
import com.marksduarte.cursomc.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {


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

        for(FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();

    }
}
