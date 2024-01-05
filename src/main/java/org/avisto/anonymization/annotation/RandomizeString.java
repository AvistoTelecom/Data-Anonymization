package org.avisto.anonymization.annotation;

import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.StringType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface RandomizeString {
    /**
     * define the format of the String to set
     * @return value
     */
    StringType value();

    /**
     * set the min size the Collection can have
     * @return value
     */
    int minSize() default StringGenerator.DEFAULT_MIN_SIZE;

    /**
     * set the max size the Collection can have
     * @return value
     */
    int maxSize() default StringGenerator.DEFAULT_MAX_SIZE;

    /**
     * set the min length the value can be
     * @return value
     */
    int minLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;

    /**
     * set the max length the value can be
     * @return value
     */
    int maxLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;

    /**
     * path of a file were each line is a possible value
     * if STRING_FROM_FILE is used it must be defined or it wil send an exception
     * @return value
     */
    String path() default "";

    /**
     * values that the field can get
     * @return value
     */
    String[] possibleValues() default {};

    /**
     * pattern for the regex generator
     * @return value
     */
    String pattern() default "[a-z]{5,12}";

    /**
     * specify whether the field has a unique key
     * @return value
     */
    boolean isUnique() default false;

    /**
     * specify if a null field should be anonymized
     * @return value
     */
    boolean randomizeNull() default false;
}
