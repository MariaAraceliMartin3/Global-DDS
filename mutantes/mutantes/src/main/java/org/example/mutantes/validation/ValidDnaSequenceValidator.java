package org.example.mutantes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator
        implements ConstraintValidator<ValidDnaSequence, String[]> {

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;
        for (String fila : dna) {
            if (fila == null || fila.length() != n) {
                return false;
            }
            for (char letra : fila.toCharArray()) {
                if (letra != 'A' && letra != 'T' && letra != 'C' && letra != 'G') {
                    return false;
                }
            }
        }
        return true;
    }
}