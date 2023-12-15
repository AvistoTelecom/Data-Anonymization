package org.avisto.anonymization.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface RandomizeEnum {
    /**
     * specify if a null field should be anonymized
     * @return value
     */
    boolean randomizeNull() default false;
}
