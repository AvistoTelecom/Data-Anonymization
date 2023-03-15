package org.avisto.anonymization.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class BadAnnotationException extends IllegalArgumentException {
    public BadAnnotationException(String message) {
        super(message);
    }

    public BadAnnotationException(String message, Throwable e) {
        super(message, e);
    }

    public BadAnnotationException(Throwable e) {
        super(e);
    }

    public BadAnnotationException(Class<?> clazz, Field field, Annotation annotation, Throwable e) {
        super(
                String.format("the annotation '%s' have wrong value on the field '%s' on class '%s'",
                annotation.toString(),
                field.toString(),
                clazz.getName()),
                e);
    }

    public BadAnnotationException(Class<?> clazz, Field field, Annotation annotation) {
        super(String.format("the annotation '%s' have wrong value on the field '%s' on class '%s'",
                        annotation.toString(),
                        field.toString(),
                        clazz.getName()));
    }
}
