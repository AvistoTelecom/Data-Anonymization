package org.avisto.anonymization.generator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that generate all type of number with random basis
 * @author desaintpern
 */
public final class NumberGenerator implements Generator{

    /**
     * Text that is used for exception when a min value is greater than max value
     */
    private static final String MIN_GREATER_THAN_MAX = "max must be greater than min";
    public static final int MIN_SIZE_COLLECTION = 1;
    public static final int MAX_SIZE_COLLECTION = 15;


    /**
     * Generate a random Float
     * @return random float
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
     * Generate a random Double
     * @return random Double
     */
    public static Double generateDouble() {
        return ((ThreadLocalRandom.current().nextDouble() - 0.5) * 2) * Double.MAX_VALUE;
    }

    /**
     * Generate a random Double, the value is between [ min, max [
     * @param min min value
     * @param max max value
     * @return random Double in [ min, max [
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
