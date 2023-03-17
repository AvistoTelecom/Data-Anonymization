package org.avisto.anonymization.model.enums;

import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.interfaces.GenerableString;

import java.util.Arrays;
import java.util.List;

public enum StringType implements GenerableString{
    STRING {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            minLength = minLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MIN_LENGTH : minLength;
            maxLength = maxLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MAX_LENGTH : maxLength;
            return StringGenerator.generateString(minLength, maxLength);
        }
    },
    LICENSE_PLATE {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            return String.format("%s-%s-%s",
                    StringGenerator.generateString(2, 2).toUpperCase(),
                    StringGenerator.generateNumber(0,999,3),
                    StringGenerator.generateString(2, 2).toUpperCase());
        }
    },
    TEXT {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            minLength = minLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MIN_LENGTH : minLength;
            maxLength = maxLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MAX_LENGTH : maxLength;
            return StringGenerator.LOREM_IPSUM.substring(0, NumberGenerator.generateInt(minLength, maxLength));
        }
    },
        SOCIAL_SECURITY_NUMBER {
            @Override
            @SuppressWarnings("unchecked")
            public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            return String.format("%s%s%s%s%s%s",
                    StringGenerator.generateNumber(1,2,1),
                    StringGenerator.generateNumber(2),
                    StringGenerator.generateNumber(1,12,2),
                    StringGenerator.generateNumber(1,99,2),
                    StringGenerator.generateNumber(3),
                    StringGenerator.generateNumber(3));
        }
    },
    PHONE_INTERNATIONAL {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            return "0" + StringGenerator.generateNumber(12);
        }
    },
    URL {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            String prefix = StringGenerator.generateStringFromCollection(Arrays.asList("https://", "http://"));
            String link = String.format("%s/%s/%s",
                    StringGenerator.generateString(5,10),
                    StringGenerator.generateString(5,10),
                    StringGenerator.generateString(5,10));
            return prefix + link;
        }
    },
    NUMBER {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            minLength = minLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MIN_LENGTH : minLength;
            maxLength = maxLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MAX_LENGTH : maxLength;
            return StringGenerator.generateNumber(NumberGenerator.generateInt(minLength, maxLength));
        }
    },
    EMAIL {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            return String.format("%s.%s@%s.%s",
                    StringGenerator.generateString(),
                    StringGenerator.generateString(),
                    StringGenerator.generateString(3,7),
                    StringGenerator.generateString(2,3));
        }
    },
    STRING_FROM_FILE {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            if (path == null || path.isEmpty()) {
                throw new IllegalArgumentException("On STRING_FROM_FILE type, path must not be empty or null");
            }
            return StringGenerator.generateStringFromFile(path);
        }
    },
    STRING_FROM_ARRAY {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues) {
            if (possibleValues == null || possibleValues.length == 0) {
                throw new IllegalArgumentException("On STRING_FROM_ARRAY type, possibleValues must not be empty or null");
            }
            return StringGenerator.generateStringFromCollection(List.of(possibleValues));
        }
    };
    public static final int DEFAULT_LENGTH_BEHAVIOR = -1;
}
