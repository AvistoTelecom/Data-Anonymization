package org.avisto.anonymization.exception;

import java.lang.reflect.Field;

public class SetterGenerationException extends RuntimeException{
    public SetterGenerationException(Field field, Exception e){
    super(
            String.format("failed to call setter method of the field : '%s'%nplease be sure that there is a setter available for this field and is named set<Fieldname> with a unique parameter that the type is the same as the field",
            field.toString()),
    e);
    }
    public SetterGenerationException(String message, Exception e){
        super(message, e);
    }
    public SetterGenerationException(Exception e){
        super(e);
    }
}
