package com.example.project.users.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.users.repository.UserRepository;

// Define the annotation
@Constraint(validatedBy = UniqueEmail.UniqueEmailValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email is already taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // Validator class
    @Component
    public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

        @Autowired
        private UserRepository userRepository; // Adjust based on your actual repository

        @Override
        public void initialize(UniqueEmail constraintAnnotation) {
        }

        @Override
        public boolean isValid(String email, ConstraintValidatorContext context) {
            if (email == null || email.isEmpty()) {
                return true; // Or false depending on how you want to handle null or empty
            }
            return !userRepository.existsByEmail(email); // Adjust based on your actual repository method
        }
    }
}
