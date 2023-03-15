package org.avisto.anonymization.anonymizer;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public interface Randomizer {

    Object randomize(Object object) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    private Number randomizeNumber(Supplier<Number> supplier) {
        return supplier.get();
    }

    private String randomizeString(Supplier<String> supplier) { return supplier.get(); }
}
