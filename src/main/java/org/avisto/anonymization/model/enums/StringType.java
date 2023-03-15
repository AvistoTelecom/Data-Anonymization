package org.avisto.anonymization.model.enums;


import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.interfaces.GenerableString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StringType implements GenerableString{
//    SINGLE_NAME {
//        @Override
//        @SuppressWarnings("unchecked")
//        public String getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            minLength = minLength==DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MIN_LENGTH : minLength;
//            maxLength = maxLength==DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MAX_LENGTH : maxLength;
//            return StringGenerator.generateString(minLength, maxLength);
//        }
//    },
//    SINGLE_EMAIL {
//        @Override
//        @SuppressWarnings("unchecked")
//        public String getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            return StringGenerator.generateEmail();
//        }
//    },
//    SINGLE_NUMBER {
//        @Override
//        @SuppressWarnings("unchecked")
//        public String getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            minLength = minLength==DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_NUMBER_MIN_LENGTH : minLength;
//            maxLength = maxLength==DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_NUMBER_MAX_LENGTH : maxLength;
//            return StringGenerator.generateNumber(minLength, maxLength);
//        }
//    },
//    SINGLE_VALUE_FROM_FILE {
//        @Override
//        @SuppressWarnings("unchecked")
//        public String getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            return StringGenerator.generateStringFromFile(path);
//        }
//    },
//    SINGLE_VALUE_FROM_ARRAY {
//        @Override
//        @SuppressWarnings("unchecked")
//        public String getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            return StringGenerator.generateStringFromCollection(Arrays.stream(possibleValues).collect(Collectors.toList()));
//        }
//    },
//    MULTIPLE_NAME {
//        @Override
//        @SuppressWarnings("unchecked")
//        public List<String> getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            minLength = minLength==DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MIN_LENGTH : minLength;
//            maxLength = maxLength==DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MAX_LENGTH : maxLength;
//            int size = NumberGenerator.generateInt(minSize, maxSize+1);
//            return StringGenerator.generateStrings(size, minLength, maxLength).collect(Collectors.toList());
//        }
//    },
//    MULTIPLE_EMAIL {
//        @Override
//        @SuppressWarnings("unchecked")
//        public List<String> getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            return ;
//        }
//    },
//    MULTIPLE_NUMBER {
//        @Override
//        @SuppressWarnings("unchecked")
//        public List<String> getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            minLength = minLength==DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_NUMBER_MIN_LENGTH : minLength;
//            maxLength = maxLength==DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_NUMBER_MAX_LENGTH : maxLength;
//            int size = NumberGenerator.generateInt(minSize, maxSize+1);
//            return StringGenerator.generateNumbers(size, minLength, maxLength).collect(Collectors.toList());
//        }
//    },
//    MULTIPLE_VALUE_FROM_FILE {
//        @Override
//        @SuppressWarnings("unchecked")
//        public List<String> getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            int size = NumberGenerator.generateInt(minSize, maxSize+1);
//            return StringGenerator.generateStringsFromFile(size, path).collect(Collectors.toList());
//        }
//    },
//    MULTIPLE_VALUE_FROM_ARRAY {
//        @Override
//        @SuppressWarnings("unchecked")
//        public List<String> getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
//            int size = NumberGenerator.generateInt(minSize, maxSize+1);
//            return StringGenerator.generateStringsFromCollection(size,
//                    Arrays.stream(possibleValues).collect(Collectors.toList()))
//                    .collect(Collectors.toList());
//        }
//    };



    public static final int DEFAULT_LENGTH_BEHAVIOR = -1;
}
