package org.avisto.anonymization.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class BadUseAnnotationException extends IllegalArgumentException {
    public BadUseAnnotationException(String message) {
        super(message);
    }

    public BadUseAnnotationException(String message, Throwable e) {
        super(message, e);
    }

    public BadUseAnnotationException(Throwable e) {
        super(e);
    }

    public BadUseAnnotationException(Class<?> clazz, Field field, Annotation annotation, Throwable e) {
        super(
                String.format("the annotation '%s' have wrong value on the field '%s' on class '%s'",
                annotation.toString(),
                field.toString(),
                clazz.getName()),
                e);
    }

    public BadUseAnnotationException(Class<?> clazz, Field field, Annotation annotation) {
        super(String.format("the annotation '%s' have wrong value on the field '%s' on class '%s'",
                        annotation.toString(),
                        field.toString(),
                        clazz.getName()));
    }
}
