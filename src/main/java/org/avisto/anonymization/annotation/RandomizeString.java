/*
 * Anonymization - This library has for main purpose to allow anonymization of Object by using annotation
 * Copyright © 2023 Advans-group (zack.de-saint-pern@advans-group.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.avisto.anonymization.annotation;

import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.StringType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RandomizeString {
    /**
     * define the format of the String to set
     */
    StringType value();

    /**
     * set the min size the Collection can have
     */
    int minSize() default StringGenerator.DEFAULT_MIN_SIZE;

    /**
     * set the max size the Collection can have
     */
    int maxSize() default StringGenerator.DEFAULT_MAX_SIZE;

    /**
     * set the min length the value can be
     */
    int minLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;

    /**
     * set the max length the value can be
     */
    int maxLength() default StringType.DEFAULT_LENGTH_BEHAVIOR;

    /**
     * path of a file were each line is a possible value
     * if STRING_FROM_FILE is used it must be defined or it wil send an exception
     */
    String path() default "";

    /**
     * values that the field can get <br/>
     */
    String[] possibleValues() default {};

    /**
     * pattern for the regex generator
     */
    String pattern() default "[a-z]{5,12}";
}
