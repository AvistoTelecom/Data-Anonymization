package org.avisto.anonymization.generator;

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
