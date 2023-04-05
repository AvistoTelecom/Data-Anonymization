/*
 *
 */

package org.avisto.anonymization.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface RandomizeEnum {

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
}
