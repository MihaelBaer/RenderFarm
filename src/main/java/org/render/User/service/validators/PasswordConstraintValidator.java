package org.render.User.validators;

import org.passay.*;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;


/**
 * Class for creating a custom password validator
 * Class implements the ConstraintValidator interface
 */
@Component
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Overriding method, which initializes the validator in preparation for isValid calls
     * @param arg0 - entered custom interface ValidPassword
     */
    @Override
    public void initialize(ValidPassword arg0) {
    }

    /**
     * Overriding method, which applies several rules to an entered password
     *
     * @param password - an entered password
     * @param context - context in which the constraint is evaluated
     * @return - true or false if value does not pass the constraint
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // at least 8 characters
                new LengthRule(8, 80),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),
                // no whitespace
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = String.join(",", messages);

        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}