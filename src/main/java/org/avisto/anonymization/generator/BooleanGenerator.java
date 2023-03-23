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
package org.avisto.anonymization.generator;

/**
 * @author desaintpern
 * Static Class that have methods to give random boolean
 */
public final class BooleanGenerator {

    /**
     * generate a random boolean with a probability equals to 50% for true and 50% for false
     * @return random boolean
     */
    public static Boolean generateBoolean() {
        return generateBoolean(0.5f);
    }

    /**
     * generate a random boolean with a probability equals to {probability} for true and { 1 - probability } for false
     * @param probability the probability to return true
     * @return random boolean
     */
    public static Boolean generateBoolean(Float probability) {
        return NumberGenerator.generateFloat(0f,1f) < probability;
    }

    /**
     * this class can't be instantiated
     */
    private BooleanGenerator() {
    }
}
