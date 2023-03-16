package org.avisto.anonymization.anonymizer;

import java.lang.reflect.InvocationTargetException;

public interface Randomizer {

    Object randomize(Object object) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;
}
