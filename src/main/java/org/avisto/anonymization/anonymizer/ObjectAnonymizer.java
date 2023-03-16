package org.avisto.anonymization.anonymizer;


import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.Init;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.annotation.RandomizeString;
import org.avisto.anonymization.exception.AnonymeException;
import org.avisto.anonymization.exception.BadUseAnnotationException;
import org.avisto.anonymization.exception.SetterGenerationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class ObjectAnonymizer implements Randomizer {
    
    public Object anonymize(Object object) {
        try {
            checkIfAnonymizable(object);
            initializeObject(object);
            return randomize(object);
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

    private void callSetterMethodHandlingException(Annotation annotation, Object object, Field field, Object newValue) {
        try {
            callSetterMethod(
                    object,
                    field,
                    newValue);
        }
        catch (NumberFormatException | NoSuchMethodException e) {
            throw new BadUseAnnotationException(object.getClass(), field, annotation, e);
        }
        catch (IllegalAccessException e) {
            throw new BadUseAnnotationException("restrict access of " + field.getName() + " doesn't allow modification");
        }
        catch (InvocationTargetException e) {
            throw new SetterGenerationException(field, e);
        }
    }

    private String genSetterName(Field field) {
        String fieldName = field.getName();
        return  "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private void numberBehavior(Object object, Field field, RandomizeNumber annotation) {
        callSetterMethodHandlingException(
                annotation,
                object,
                field,
                annotation.value().getRandomValue(
                        annotation.minValue(),
                        annotation.maxValue(),
                        annotation.minSize(),
                        annotation.maxSize()
                )
        );
    }
    private void stringBehavior(Object object, Field field, RandomizeString annotation) {
        callSetterMethodHandlingException(
                annotation,
                object,
                field,
                annotation.value().getRandomValue(
                        annotation.minSize(),
                        annotation.maxSize(),
                        annotation.minLength(),
                        annotation.maxLength(),
                        annotation.path(),
                        annotation.possibleValues()
                )
        );
    }

    @Override
    public Object randomize(Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomizeNumber.class)) {
                RandomizeNumber annotation = field.getAnnotation(RandomizeNumber.class);
                numberBehavior(object, field, annotation);
            }
             else if (field.isAnnotationPresent(RandomizeString.class)) {
                RandomizeString annotation = field.getAnnotation(RandomizeString.class);
                stringBehavior(object, field, annotation);
            }
        }
        return object;
    }
}
