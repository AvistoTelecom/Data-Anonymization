package org.avisto.anonymization.anonymizer;


import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.Init;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.exception.AnonymeException;
import org.avisto.anonymization.exception.BadAnnotationException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class ObjectAnonymizer implements Randomizer {
    
    public void anonymize(Object object) {
        try {
            checkIfAnonymizable(object);
            initializeObject(object);
            randomize(object);
        } catch (Exception e) {
            throw new AnonymeException(e);
        }
    }


    private void checkIfAnonymizable(Object object) {
        if (Objects.isNull(object)) {
            throw new AnonymeException("The object to anonymize is null");
        }

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(Anonyme.class)) {
            throw new AnonymeException("The class "
                    + clazz.getSimpleName()
                    + " is not annotated with Anonyme");
        }
    }

    private void initializeObject(Object object) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.invoke(object);
            }
        }
    }

    private void callSetterMethod(Object object, Field field, Object newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        String setterName = genSetterName(field);
        clazz.getMethod(setterName, field.getType()).invoke(object, newValue);
    }

    private String genSetterName(Field field) {
        String fieldName = field.getName();
        return  "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    @Override
    public Object randomize(Object object) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomizeNumber.class)) {
                RandomizeNumber annotation = field.getAnnotation(RandomizeNumber.class);
                try {
                    callSetterMethod(
                            object,
                            field,
                            annotation.type().getRandomValue(
                                    annotation.minValue(),
                                    annotation.maxValue(),
                                    annotation.minSize(),
                                    annotation.maxSize()));
                }
                catch (NumberFormatException | NoSuchMethodException e) {
                    throw new BadAnnotationException(clazz, field, annotation, e);
                }
            }
        }
        return object;
    }
}
