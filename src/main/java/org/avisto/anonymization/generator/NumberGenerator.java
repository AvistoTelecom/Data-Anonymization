package org.avisto.anonymization.generator;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
     * Generate a Stream of size {size} random Float, values are between [ -Float.MAX_VALUE, Float.MAX_VALUE [
     * @param size the number of Float to generate
     * @return Stream of {size} element  of random Float in [ -Float.MAX_VALUE, Float.MAX_VALUE [
     */
    public static Stream<Float> generateFloats(Integer size) {
        Float[] res = new Float[size];
        for (int i = 0; i < size; i++) {
            res[i] = generateFloat();
        }
        return Arrays.stream(res);
    }

    /**
     * Generate a Stream of size {size} random Float, values are between [ min, max [
     * @param size the number of Float to generate
     * @param min min value
     * @param max max value
     * @return Stream of {size} element  of random Float in [ min, max [
     */
    public static Stream<Float> generateFloats(Integer size, Float min, Float max) {
        Float[] res = new Float[size];
        for (int i = 0; i < size; i++) {
            res[i] = generateFloat(min, max);
        }
        return Arrays.stream(res);
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
     * Generate a Stream of size {size} random Double, values are between [ -Double.MAX_VALUE, Double.MAX_VALUE [
     * @param size the number of Double to generate
     * @return Stream of {size} element  of random Double in [ -Double.MAX_VALUE, Double.MAX_VALUE [
     */
    public static Stream<Double> generateDoubles(Integer size) {
        Double[] res = new Double[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = generateDouble();
        }
        return Arrays.stream(res);
    }

    /**
     * Generate a stream of size {size} random Double, values are between [ min, max [
     * @param size the number of Double to generate
     * @param min min value
     * @param max max value
     * @return Stream of {size} element  of random Double in [ min, max [
     */
    public static Stream<Double> generateDoubles(Integer size, Double min, Double max) {
        Double[] res = new Double[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = generateDouble(min, max);
        }
        return Arrays.stream(res);
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
     * Generate a Stream of size {size} random Integer
     * @param size the number of Integer to generate
     * @return Stream of {size} element  of random Integer
     */
    public static Stream<Integer> generateInts(Integer size) {
        return generateInts(size, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Generate a Stream of size {size} random Integer, values are between [ min, max [
     * @param size the number of Integer to generate
     * @param min min value
     * @param max max value
     * @return Stream of {size} element  of random Integer in [ min, max [
     */
    public static Stream<Integer> generateInts(Integer size, Integer min, Integer max) {
        Integer[] res = new Integer[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = generateInt(min, max);
        }
        return Arrays.stream(res);
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
     * Generate a Stream of size {size} random Long
     * @param size the number of Long to generate
     * @return Stream of {size} element  of random Long
     */
    public static Stream<Long> generateLongs(Integer size) {
        return generateLongs(size, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Generate a Stream of size {size} random Long, values are between [ min, max [
     * @param size the number of Long to generate
     * @param min min value
     * @param max max value
     * @return Stream of {size} element  of random Long in [ min, max [
     */
    public static Stream<Long> generateLongs(Integer size, Long min, Long max) {
        Long[] res = new Long[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = generateLong(min, max);
        }
        return Arrays.stream(res);
    }

    /**
     * this class can't be instantiated
     */
    private NumberGenerator() {
    }
}
