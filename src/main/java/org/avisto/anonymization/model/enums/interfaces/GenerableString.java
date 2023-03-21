package org.avisto.anonymization.model.enums.interfaces;

import org.avisto.rgxgen.RgxGen;

public interface GenerableString {
    <T> T getRandomValue(int minLength, int maxLength, String path, String[] possibleValues, RgxGen generator);
}
