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

import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.NumberType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
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
