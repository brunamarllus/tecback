package br.uniesp.si.techback.validation.impl;

import br.uniesp.si.techback.validation.annotation.CPFouCNPJValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFouCNPJValidator
        implements ConstraintValidator<
        CPFouCNPJValido,
        String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {

        if (value == null || value.isBlank()) {
            return true;
        }

        String numero =
                value.replaceAll("\\D", "");

        return numero.length() == 11
                || numero.length() == 14;
    }
}
