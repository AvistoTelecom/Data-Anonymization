package org.avisto.anonymization.annotation;

import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.NumberType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface RandomizeNumber {

    /**
     * define the Type of number to set
     * @return value
     */
    NumberType value();


    /**
     * set the min value available.
     * the value must fit the syntax of the NumberType selected
     * @return value
     */
    String minValue() default NumberType.BEHAVIOR;

    /**
     * set the max value exclude available.
     * the value must fit the syntax of the NumberType selected
     * @return value
     */
    String maxValue() default NumberType.BEHAVIOR;

    /**
     * set the min size the Collection can have
     * @return value
     */
    int minSize() default NumberGenerator.MIN_SIZE_COLLECTION;
    /**
     * set the max size the Collection can have
     * @return value
     */
    int maxSize() default NumberGenerator.MAX_SIZE_COLLECTION;

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
