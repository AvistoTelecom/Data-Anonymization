package org.avisto.anonymization.annotation;

import org.avisto.anonymization.model.enums.StringType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface RandomizeFile {

    /**
     * directory where to save file
     * @return value
     */
    String pathToDirectory();

    /**
     * behavior of how to generate name of the new file
     * @return value
     */
    RandomizeString nameFileBehavior() default @RandomizeString(value = StringType.STRING, minLength = 15, maxLength = 30);

    /**
     * set the min size the Collection can have
     * @return value
     */
    int minSize() default 1;
    /**
     * set the max size the Collection can have
     * @return value
     */
    int maxSize() default 10;

    /**
     * define if the old file must be removed
     * @return value
     */
    boolean removeOld() default true;

}
