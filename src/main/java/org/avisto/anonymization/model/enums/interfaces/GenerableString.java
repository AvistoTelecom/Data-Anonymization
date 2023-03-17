package org.avisto.anonymization.model.enums.interfaces;

public interface GenerableString {
    <T> T getRandomValue(int minLength, int maxLength, String path, String[] possibleValues);
}
