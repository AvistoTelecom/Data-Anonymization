package org.avisto.anonymization.model.enums;


import org.avisto.anonymization.model.enums.interfaces.GenerableString;

public enum StringType implements GenerableString{
    ;
    public static final int DEFAULT_LENGTH_BEHAVIOR = -1;

    @Override
    public <T> T getRandomValue(int minSize, int maxSize, int minLength, int maxLength, String path, String[] possibleValues) {
        return null;
    }
}
