package org.avisto.anonymization.annotation;


import org.avisto.anonymization.anonymizer.Randomizer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Init {
    Class<? extends Randomizer> machin();

}
