package org.avisto.anonymization.generator;

import java.util.List;

public interface Generator {


    /**
     * select a random value in the parameter origin
     * @param origin List of possible value
     * @return the random value selected in origin
     */
    static <T> T generateValueFromCollection(List<T> origin) {
        return origin.get(NumberGenerator.generateInt(0, origin.size()));
    }

    /**
     * select a random value in the parameter origin
     * @param origin array of possible value
     * @return the random value selected in origin
     */
    static <T> T generateValueFromCollection(T[] origin) {
        return origin[NumberGenerator.generateInt(0, origin.length)] ;
    }
}
