package org.avisto.anonymization.annotation;

import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.NumberType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RandomizeNumber {

    /**
     * define the Type of number to set
     */
    NumberType value();


    /**
     * set the min value available. <br/>
     * the value must fit the syntax of the NumberType selected
     */
    String minValue() default NumberType.BEHAVIOR;

    /**
     * set the max value exclude available. <br/>
     * the value must fit the syntax of the NumberType selected
     */
    String maxValue() default NumberType.BEHAVIOR;

    /**
     * set the min size the Collection can have
     */
    int minSize() default NumberGenerator.MIN_SIZE_COLLECTION;
    /**
     * set the max size the Collection can have
     */
    int maxSize() default NumberGenerator.MAX_SIZE_COLLECTION;
}
