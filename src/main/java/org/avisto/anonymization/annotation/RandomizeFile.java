/*
 *
 */

package org.avisto.anonymization.annotation;

import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.StringType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface RandomizeFile {

    /**
     * directory where to save file
     */
    String pathToDirectory();

    /**
     * behavior of how to generate name of the new file
     */
    RandomizeString nameFileBehavior() default @RandomizeString(value = StringType.STRING, minLength = 15, maxLength = 30);

    /**
     * set the min size the Collection can have
     */
    int minSize() default StringGenerator.DEFAULT_MIN_SIZE;
    /**
     * set the max size the Collection can have
     */
    int maxSize() default StringGenerator.DEFAULT_MAX_SIZE;

    /**
     * define if the old file must be removed
     */
    boolean removeOld() default true;

}
