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

    NumberType value();
    String minValue() default NumberType.BEHAVIOR;
    String maxValue() default NumberType.BEHAVIOR;
    int minSize() default NumberGenerator.MIN_SIZE_COLLECTION;
    int maxSize() default NumberGenerator.MAX_SIZE_COLLECTION;
}
