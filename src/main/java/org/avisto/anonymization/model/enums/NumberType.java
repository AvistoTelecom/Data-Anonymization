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
package org.avisto.anonymization.model.enums;

import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.interfaces.GenerableNumber;

/**
 * @author desaintpern
 * Enum that represent all number type randomizable
 */
public enum NumberType implements GenerableNumber {

    INT {
        @Override
        @SuppressWarnings("unchecked")
        public Integer getRandomValue(String min, String max) {
            Integer minValue = min.equals(BEHAVIOR) ? Integer.MIN_VALUE : Integer.parseInt(min);
            Integer maxValue = max.equals(BEHAVIOR) ? Integer.MAX_VALUE : Integer.parseInt(max);
            return NumberGenerator.generateInt(minValue, maxValue);
        }
    },
    FLOAT {
        @Override
        @SuppressWarnings("unchecked")
        public Float getRandomValue(String min, String max) {
            Float minValue = min.equals(BEHAVIOR) ? -Float.MAX_VALUE : Float.parseFloat(min);
            Float maxValue = max.equals(BEHAVIOR) ? Float.MAX_VALUE : Float.parseFloat(max);
            return NumberGenerator.generateFloat(minValue, maxValue); }
    },
    DOUBLE {
        @Override
        @SuppressWarnings("unchecked")
        public Double getRandomValue(String min, String max) {
            Double minValue = min.equals(BEHAVIOR) ? Double.MIN_VALUE : Double.parseDouble(min);
            Double maxValue = max.equals(BEHAVIOR) ? Double.MAX_VALUE : Double.parseDouble(max);
            return NumberGenerator.generateDouble(minValue, maxValue); }
    },
    LONG {
        @Override
        @SuppressWarnings("unchecked")
        public Long getRandomValue(String min, String max) {
            Long minValue = min.equals(BEHAVIOR) ? Long.MIN_VALUE : Long.parseLong(min);
            Long maxValue = max.equals(BEHAVIOR) ? Long.MAX_VALUE : Long.parseLong(max);
            return NumberGenerator.generateLong(minValue, maxValue); }
    };

    public static final String BEHAVIOR = "default";
}
