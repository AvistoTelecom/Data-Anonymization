package org.avisto.anonymization.model.enums.interfaces;

public interface GenerableNumber {
    <T> T getRandomValue(String min, String max, int minSize, int maxSize);
}
