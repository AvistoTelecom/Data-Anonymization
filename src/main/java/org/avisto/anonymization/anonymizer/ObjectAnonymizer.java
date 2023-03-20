package org.avisto.anonymization.anonymizer;


import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.annotation.RandomizeString;
import org.avisto.anonymization.exception.AnonymeException;
import org.avisto.anonymization.exception.BadUseAnnotationException;
import org.avisto.anonymization.exception.SetterGenerationException;
import org.avisto.anonymization.generator.NumberGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author desaintpern
 * Class that analyse and randomize object correctly annotated
 */
public class ObjectAnonymizer implements Randomizer {
    
    public <T> void anonymize(T object) {
        try {
            checkIfAnonymizable(object);
            randomize(object);
        } catch (Exception e) {
            throw new AnonymeException(e);
        }
    }

    public <T> void anonymize(Iterable<T> objectCollection) {
        try {
            objectCollection.forEach(this::anonymize);
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
        Object newValue = genNewValue(field.getType(),
                () -> annotation.value().getRandomValue(
                        annotation.minValue(),
                        annotation.maxValue()),
                NumberGenerator.generateInt(annotation.minSize(), annotation.maxSize()));
        callSetterMethodHandlingException(
                annotation,
                object,
                field,
                newValue);
    }
    private void stringBehavior(Object object, Field field, RandomizeString annotation) {
        Object newValue = genNewValue(field.getType(),
                () -> annotation.value().getRandomValue(
                        annotation.minLength(),
                        annotation.maxLength(),
                        annotation.path(),
                        annotation.possibleValues()),
                NumberGenerator.generateInt(annotation.minSize(), annotation.maxSize()));

        callSetterMethodHandlingException(
                annotation,
                object,
                field,
                newValue);
    }

    private <T> Object genNewValue(Class<?> fieldType, Supplier<T> supplier, int size) {
        if (!(Iterable.class.isAssignableFrom(fieldType))) {
            return supplier.get();
        } else if (ArrayList.class.isAssignableFrom(fieldType)) {
            return Stream.generate(supplier).limit(size).collect(Collectors.toCollection(ArrayList::new));
        } else if (LinkedList.class.isAssignableFrom(fieldType)) {
            return Stream.generate(supplier).limit(size).collect(Collectors.toCollection(LinkedList::new));
        } else if (List.class.isAssignableFrom(fieldType)) {
            return Stream.generate(supplier).limit(size).collect(Collectors.toList());
        } else throw new BadUseAnnotationException("Type not supported yet");

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
