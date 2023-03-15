package org.avisto.anonymization.annotation;

import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.StringType;

public @interface RandomizeString {
    StringType type();
    int minSize() default StringGenerator.DEFAULT_MIN_SIZE;
    int maxSize() default StringGenerator.DEFAULT_MAX_SIZE;
    int minLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;
    int maxLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;
    String path() default "";
    String[] possibleValues() default {};
}
