package org.avisto.anonymization.model.enums;

import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.interfaces.GenerableNumber;

import java.util.List;
import java.util.stream.Collectors;

public enum NumberType implements GenerableNumber {

    INT {
        @Override
        @SuppressWarnings("unchecked")
        public Integer getRandomValue(String min, String max, int minSize, int maxSize) {
            Integer minValue = min.equals(BEHAVIOR) ? Integer.MIN_VALUE : Integer.parseInt(min);
            Integer maxValue = max.equals(BEHAVIOR) ? Integer.MAX_VALUE : Integer.parseInt(max);
            return NumberGenerator.generateInt(minValue, maxValue);
        }
    },
    FLOAT {
        @Override
        @SuppressWarnings("unchecked")
        public Float getRandomValue(String min, String max, int minSize, int maxSize) {
            Float minValue = min.equals(BEHAVIOR) ? -Float.MAX_VALUE : Float.parseFloat(min);
            Float maxValue = max.equals(BEHAVIOR) ? Float.MAX_VALUE : Float.parseFloat(max);
            return NumberGenerator.generateFloat(minValue, maxValue); }
    },
    DOUBLE {
        @Override
        @SuppressWarnings("unchecked")
        public Double getRandomValue(String min, String max, int minSize, int maxSize) {
            Double minValue = min.equals(BEHAVIOR) ? Double.MIN_VALUE : Double.parseDouble(min);
            Double maxValue = max.equals(BEHAVIOR) ? Double.MAX_VALUE : Double.parseDouble(max);
            return NumberGenerator.generateDouble(minValue, maxValue); }
    },
    LONG {
        @Override
        @SuppressWarnings("unchecked")
        public Long getRandomValue(String min, String max, int minSize, int maxSize) {
            Long minValue = min.equals(BEHAVIOR) ? Long.MIN_VALUE : Long.parseLong(min);
            Long maxValue = max.equals(BEHAVIOR) ? Long.MAX_VALUE : Long.parseLong(max);
            return NumberGenerator.generateLong(minValue, maxValue); }
    },
    INT_LIST {
        @Override
        @SuppressWarnings("unchecked")
        public List<Integer> getRandomValue(String min, String max, int minSize, int maxSize) {
            Integer minValue = min.equals(BEHAVIOR) ? Integer.MIN_VALUE : Integer.parseInt(min);
            Integer maxValue = max.equals(BEHAVIOR) ? Integer.MAX_VALUE : Integer.parseInt(max);
            int size = NumberGenerator.generateInt(minSize, maxSize+1);
            return NumberGenerator.generateInts(size, minValue, maxValue).collect(Collectors.toList());
        }
    },
    FLOAT_LIST {
        @Override
        @SuppressWarnings("unchecked")
        public List<Float> getRandomValue(String min, String max, int minSize, int maxSize) {
            Float minValue = min.equals(BEHAVIOR) ? -Float.MAX_VALUE : Float.parseFloat(min);
            Float maxValue = max.equals(BEHAVIOR) ? Float.MAX_VALUE : Float.parseFloat(max);
            int size = NumberGenerator.generateInt(minSize, maxSize+1);
            return NumberGenerator.generateFloats(size, minValue, maxValue).collect(Collectors.toList()); }
    },
    DOUBLE_LIST {
        @Override
        @SuppressWarnings("unchecked")
        public List<Double> getRandomValue(String min, String max, int minSize, int maxSize) {
            Double minValue = min.equals(BEHAVIOR) ? Double.MIN_VALUE : Double.parseDouble(min);
            Double maxValue = max.equals(BEHAVIOR) ? Double.MAX_VALUE : Double.parseDouble(max);
            int size = NumberGenerator.generateInt(minSize, maxSize+1);
            return NumberGenerator.generateDoubles(size, minValue, maxValue).collect(Collectors.toList()); }
    },
    LONG_LIST {
        @Override
        @SuppressWarnings("unchecked")
        public List<Long> getRandomValue(String min, String max, int minSize, int maxSize) {
            Long minValue = min.equals(BEHAVIOR) ? Long.MIN_VALUE : Long.parseLong(min);
            Long maxValue = max.equals(BEHAVIOR) ? Long.MAX_VALUE : Long.parseLong(max);
            int size = NumberGenerator.generateInt(minSize, maxSize+1);
            return NumberGenerator.generateLongs(size, minValue, maxValue).collect(Collectors.toList()); }
    };

    public static final String BEHAVIOR = "default";
}
