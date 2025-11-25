package org.example.mutantes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
public @interface ValidDnaSequence {
    String message() default "DNA inválido: debe ser matriz NxN con solo A,T,C,G";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}