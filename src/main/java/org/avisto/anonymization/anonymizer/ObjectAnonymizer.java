package org.avisto.anonymization.anonymizer;


import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.annotation.RandomizeString;
import org.avisto.anonymization.exception.AnonymeException;
import org.avisto.anonymization.exception.BadUseAnnotationException;
import org.avisto.anonymization.exception.MethodGenerationException;
import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.StringType;
import org.avisto.rgxgen.RgxGen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author desaintpern
 * Class that analyse and randomize object correctly annotated
 */
public class ObjectAnonymizer implements Randomizer {

    private RgxGen rgxGen;
    
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


    private <T> void checkIfAnonymizable(T object) {
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

    private <T> void callSetterMethod(Annotation annotation, T object, Field field, Object newValue) {
        try {
            Class<?> clazz = object.getClass();
            String setterName = genSetterName(field);
            clazz.getMethod(setterName, field.getType()).invoke(object, newValue);
        }
        catch (NumberFormatException | NoSuchMethodException e) {
            throw new BadUseAnnotationException(object.getClass(), field, annotation, e);
        }
        catch (IllegalAccessException e) {
            throw new BadUseAnnotationException("restrict access of " + field.getName() + " doesn't allow modification");
        }
        catch (InvocationTargetException e) {

            throw new MethodGenerationException(String.format("failed to call setter method of the field : '%s'%nplease be sure that there is a setter available for this field and is named set<Fieldname> with a unique parameter that the type is the same as the field",
                    field), e);
        }
    }

    private String genSetterName(Field field) {
        String fieldName = field.getName();
        return  "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    @SuppressWarnings("unchecked")
    private <T, R> R callGetterMethod(T object, Field field){
        try {
            return (R) object.getClass().getMethod(genGetterName(field)).invoke(object);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new MethodGenerationException(String.format("failed to call getter method of the field : '%s'%nplease be sure that there is a getter available for this field and is named get<Fieldname> with a no parameter",
                    field),
                    e);
        } catch (IllegalAccessException e) {
            throw new BadUseAnnotationException("restrict access of " + field.getName() + " doesn't allow modification");
        }
    }

    private String genGetterName(Field field) {
        String fieldName = field.getName();
        return  "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private <T> void numberBehavior(T object, Field field, RandomizeNumber annotation) {
        genNewValue(object,
                field,
                () -> annotation.value().getRandomValue(
                        annotation.minValue(),
                        annotation.maxValue()),
                NumberGenerator.generateInt(annotation.minSize(), annotation.maxSize()),
                annotation);
    }
    private <T> void stringBehavior(T object, Field field, RandomizeString annotation) {
        if (annotation.value().equals(StringType.REGEX)) rgxGen = new RgxGen(annotation.pattern());
        genNewValue(object,
                field,
                () -> annotation.value().getRandomValue(
                        annotation.minLength(),
                        annotation.maxLength(),
                        annotation.path(),
                        annotation.possibleValues(),
                        rgxGen),
                NumberGenerator.generateInt(annotation.minSize(), annotation.maxSize()),
                annotation);
    }

    private <T> void genNewValue(T object ,Field field, Supplier<T> supplier, int size, Annotation annotation) {
        if (callGetterMethod(object, field) != null) {
            if (!(Iterable.class.isAssignableFrom(field.getType()))) {
                callSetterMethod(annotation, object, field, supplier.get());
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                Collection<T> res = callGetterMethod(object, field);
                for (int i = 0; i < size; i++) {
                    res.add(supplier.get());
                }
            } else throw new BadUseAnnotationException("Type not supported yet");
        }
    }

    @Override
    public <T> void randomize(T object) {
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
    }
}
