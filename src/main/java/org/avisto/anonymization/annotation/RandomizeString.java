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
    /**
     * define the format of the String to set
     */
    StringType value();

    /**
     * set the min size the Collection can have
     */
    int minSize() default StringGenerator.DEFAULT_MIN_SIZE;

    /**
     * set the max size the Collection can have
     */
    int maxSize() default StringGenerator.DEFAULT_MAX_SIZE;

    /**
     * set the min length the value can be
     */
    int minLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;

    /**
     * set the max length the value can be
     */
    int maxLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;

    /**
     * path of a file were each line is a possible value
     * if STRING_FROM_FILE is used it must be defined or it wil send an exception
     */
    String path() default "";

    /**
     * values that the field can get <br/>
     */
    String[] possibleValues() default {};

    /**
     * pattern for the regex generator
     */
    String pattern() default "[a-z]{5,12}";
}
