package org.avisto.anonymization.annotation;

import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.StringType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RandomizeString {
    StringType value();
    int minSize() default StringGenerator.DEFAULT_MIN_SIZE;
    int maxSize() default StringGenerator.DEFAULT_MAX_SIZE;
    int minLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;
    int maxLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;
    String path() default "";
    String[] possibleValues() default {};
}
