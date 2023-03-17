package org.avisto.anonymization.model.enums;

import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.interfaces.GenerableNumber;

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
