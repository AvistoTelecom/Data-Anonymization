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

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that generate all type of number with random basis
 * @author desaintpern
 */
public final class NumberGenerator {

    /**
     * Text that is used for exception when a min value is greater than max value
     */
    private static final String MIN_GREATER_THAN_MAX = "max must be greater than min";
    public static final int MIN_SIZE_COLLECTION = 1;
    public static final int MAX_SIZE_COLLECTION = 15;


    /**
     * Generate a random Float, the value is between [ -Float.MAX_VALUE, Float.MAX_VALUE [
     * @return random float in [ -Float.MAX_VALUE, Float.MAX_VALUE [
     */
    public static Float generateFloat() {
        return ((ThreadLocalRandom.current().nextFloat() - 0.5f) * 2) * Float.MAX_VALUE;
    }

    /**
     * Generate a random Float, the value is between [ min, max [
     * @param min min value
     * @param max max value
     * @return random Float in [ min, max [
     */
    public static Float generateFloat(Float min, Float max) {
        if (min >= max) {
            throw new IllegalArgumentException(MIN_GREATER_THAN_MAX);
        }
        return ThreadLocalRandom.current().nextFloat() * (max - min) + min;
    }


    /**
     * Generate a random Double, the value is between [ -Double.MAX_VALUE, Double.MAX_VALUE [
     * @return random Double in [ -Double.MAX_VALUE, Double.MAX_VALUE [
     */
    public static Double generateDouble() {
        return ((ThreadLocalRandom.current().nextDouble() - 0.5) * 2) * Double.MAX_VALUE;
    }

    /**
     * Generate a random Double, the value is between [ -Double.MAX_VALUE, Double.MAX_VALUE [
     * @param min min value
     * @param max max value
     * @return random Double in [ -Double.MAX_VALUE, Double.MAX_VALUE [
     */
    public static Double generateDouble(Double min, Double max ) {
        if (min >= max) {
            throw new IllegalArgumentException(MIN_GREATER_THAN_MAX);
        }
        return min + ThreadLocalRandom.current().nextDouble() * (max - min);
    }



    /**
     * Generate a random Integer
     * @return random Integer
     */
    public static Integer generateInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * Generate a random Integer, the value is between [ min, max [
     * @param min min value
     * @param max max value
     * @return random Integer in [ min, max [
     */
    public static Integer generateInt(Integer min, Integer max) {
        if (min >= max) {
            throw new IllegalArgumentException(MIN_GREATER_THAN_MAX);
        }
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Generate a random Long
     * @return random Long
     */
    public static Long generateLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * Generate a random Long, the value is between [ min, max [
     * @param min min value
     * @param max max value
     * @return random Long in [ min, max [
     */
    public static Long generateLong(Long min, Long max) {
        if (min >= max) {
            throw new IllegalArgumentException(MIN_GREATER_THAN_MAX);
        }
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    /**
     * this class can't be instantiated
     */
    private NumberGenerator() {
    }
}
