package org.avisto.anonymization.model.enums;

import com.github.curiousoddman.rgxgen.RgxGen;
import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.interfaces.GenerableString;

import java.util.Arrays;
import java.util.List;

/**
 * @author desaintpern
 * Enum that represent all String format randomizable
 */
public enum StringType implements GenerableString{
    STRING {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            minLength = minLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MIN_LENGTH : minLength;
            maxLength = maxLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MAX_LENGTH : maxLength;
            return StringGenerator.generateString(minLength, maxLength);
        }
    },
    LICENSE_PLATE {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            return String.format("%s-%s-%s",
                    StringGenerator.generateString(2, 2).toUpperCase(),
                    StringGenerator.generateNumber(0,999,3),
                    StringGenerator.generateString(2, 2).toUpperCase());
        }
    },
    TEXT {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            minLength = minLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MIN_LENGTH : minLength;
            maxLength = maxLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MAX_LENGTH : maxLength;
            return StringGenerator.LOREM_IPSUM.substring(0, NumberGenerator.generateInt(minLength, maxLength));
        }
    },
        SOCIAL_SECURITY_NUMBER {
            @Override
            @SuppressWarnings("unchecked")
            public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
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
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            return "0" + StringGenerator.generateNumber(12);
        }
    },
    URL {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
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
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            minLength = minLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MIN_LENGTH : minLength;
            maxLength = maxLength == DEFAULT_LENGTH_BEHAVIOR ? StringGenerator.DEFAULT_MAX_LENGTH : maxLength;
            return StringGenerator.generateNumber(NumberGenerator.generateInt(minLength, maxLength));
        }
    },
    EMAIL {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
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
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            if (path == null || path.isEmpty()) {
                throw new IllegalArgumentException("On STRING_FROM_FILE type, path must not be empty or null");
            }
            return StringGenerator.generateStringFromFile(path);
        }
    },
    STRING_FROM_ARRAY {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            if (possibleValues == null || possibleValues.length == 0) {
                throw new IllegalArgumentException("On STRING_FROM_ARRAY type, possibleValues must not be empty or null");
            }
            return StringGenerator.generateStringFromCollection(List.of(possibleValues));
        }
    },
    REGEX {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            return generator.generate();
        }
    },
    IPV4 {
        @Override
        @SuppressWarnings("unchecked")
        public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {
            return String.format("%s.%s.%s.%s",
                    NumberGenerator.generateInt(0, 256),
                    NumberGenerator.generateInt(0, 256),
                    NumberGenerator.generateInt(0, 256),
                    NumberGenerator.generateInt(0, 256));
        }
    },
        IPV6 {
            @Override
            @SuppressWarnings("unchecked")
            public String getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator) {

                return String.format("%s:%s:%s:%s:%s:%s:%s:%s",
                        Integer.toHexString(NumberGenerator.generateInt(0, 65536)),
                        Integer.toHexString(NumberGenerator.generateInt(0, 65536)),
                        Integer.toHexString(NumberGenerator.generateInt(0, 65536)),
                        Integer.toHexString(NumberGenerator.generateInt(0, 65536)),
                        Integer.toHexString(NumberGenerator.generateInt(0, 65536)),
                        Integer.toHexString(NumberGenerator.generateInt(0, 65536)),
                        Integer.toHexString(NumberGenerator.generateInt(0, 65536)),
                        Integer.toHexString(NumberGenerator.generateInt(0, 65536)));
            }
    };
    public static final int DEFAULT_LENGTH_BEHAVIOR = -1;
}
