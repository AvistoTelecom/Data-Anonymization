package org.avisto.anonymization.anonymizer;


import com.github.curiousoddman.rgxgen.RgxGen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeEnum;
import org.avisto.anonymization.annotation.RandomizeFile;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.annotation.RandomizeString;
import org.avisto.anonymization.annotation.SelfImplementation;
import org.avisto.anonymization.annotation.Unique;
import org.avisto.anonymization.exception.AnonymeException;
import org.avisto.anonymization.exception.BadUseAnnotationException;
import org.avisto.anonymization.exception.MethodGenerationException;
import org.avisto.anonymization.exception.UniqueException;
import org.avisto.anonymization.generator.FileGenerator;
import org.avisto.anonymization.generator.Generator;
import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.StringType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author desaintpern
 * Class that analyse and randomize object correctly annotated
 */
public class ObjectAnonymizer implements Randomizer {

    private RgxGen rgxGen;

    private Map<String, Set<String>> StringMap;

    public ObjectAnonymizer() {
        this.StringMap = new HashMap<>();
    }

    /**
     * anonymize the object given.
     * @param object object to anonymize
     */
    public <T> void anonymize(T object) {
        try {
            checkIfAnonymizable(object);
            randomize(object);
        }
        catch (IllegalArgumentException e) {
            throw new BadUseAnnotationException(e);
        }
    }

    /**
     * anonymize the objects given.
     * @param objectCollection objects to anonymize
     */
    public <T> void anonymize(Iterable<T> objectCollection) {
        if (Objects.isNull(objectCollection)) { throw new BadUseAnnotationException("The object to anonymize is null"); }
        try {
            objectCollection.forEach(this::anonymize);
        }
        catch (IllegalArgumentException e) {
            throw new BadUseAnnotationException(e);
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

    private <T> void callSetterMethod(T object, Field field, Object newValue) {
        Class<?> clazz = object.getClass();
        String setterName = genSetterName(field);
        try {
            clazz.getMethod(setterName, field.getType()).invoke(object, newValue);
        }
        catch (IllegalAccessException e) {
            throw new BadUseAnnotationException("restrict access of " + field.getName() + " doesn't allow modification");
        }
        catch (InvocationTargetException | NoSuchMethodException e) {

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

    private <T> T numberBehavior(RandomizeNumber annotation) {
        return annotation.value().getRandomValue(
                        annotation.minValue(),
                        annotation.maxValue());
    }
    private <T> T stringBehavior(RandomizeString annotation) {
        if (annotation.value().equals(StringType.REGEX)) rgxGen = new RgxGen(annotation.pattern());
        return annotation.value().getRandomValue(
                        annotation.minLength(),
                        annotation.maxLength(),
                        annotation.path(),
                        annotation.possibleValues(),
                        rgxGen);
    }

    private <T> String fileBehavior(RandomizeFile annotation, T object, Field field) {
        String originalFile = callGetterMethod(object, field);
        if (annotation.removeOld()) FileGenerator.deleteFile(originalFile);
        return FileGenerator.generateFile(
                annotation.pathToDirectory(),
                stringBehavior(annotation.nameFileBehavior()),
                FileGenerator.getExtension(originalFile));
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> T enumBehavior(Field field) {
        Class<?> type = field.getType();
        if (Enum.class.isAssignableFrom(type)) {
            T[] values = (T[]) type.getEnumConstants();
            return Generator.generateValueFromCollection(values);
        } else {
            throw new BadUseAnnotationException("field should be enumeration");
        }
    }

    private <T> Object isUnique(Field field, T object, Class<?> clazz, Supplier<T> supplier, int i,  Object newValue) {
        String StringField = clazz.getName() + '.' + field.getName();
        if (!StringMap.containsKey(StringField)) {
            StringMap.put(StringField, new HashSet<>());
            StringMap.get(StringField).add(callGetterMethod(object, field).toString());
            return newValue;
        }
        if (StringMap.get(StringField).add(newValue.toString())) {
            return newValue;
        }
        if (i == 100) {
            throw new UniqueException(String.format("Can't anonymize this field who contain a unique key", field.getName(), clazz));
        }
        isUnique(field, object, clazz, supplier, i + 1, supplier.get());
        return newValue;
    }

    private <T> void setNewValue(T object , Field field, Supplier<T> supplier, int size, boolean isUnique, Class<?> clazz, boolean randomizeNull) {
        Object newValue = isUnique ? isUnique(field, object, clazz, supplier, 0, supplier.get()) : supplier.get();
        if (callGetterMethod(object, field) != null || randomizeNull) {
            if (!(Iterable.class.isAssignableFrom(field.getType()))) {
                callSetterMethod(object, field, newValue);
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                Collection<Object> res = callGetterMethod(object, field);
                for (int i = 0; i < size; i++) {
                    newValue = supplier.get();
                    if (isUnique) {
                        newValue = isUnique(field, object, clazz, supplier, 0, newValue);
                    }
                    res.add(newValue);
                }
            } else {
                throw new BadUseAnnotationException("Type not supported yet");
            }
        }
    }

    private void callMethod(Object object, Method method) {
        try {
            method.invoke(object);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new BadUseAnnotationException(String.format("Method %s on class %s couldn't be called", method.getName(), object.getClass()));
        }
    }

    @Override
    public <T> void randomize(T object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomizeNumber.class)) {
                RandomizeNumber annotation = field.getAnnotation(RandomizeNumber.class);
                setNewValue(object,
                    field,
                    () -> numberBehavior(annotation),
                    NumberGenerator.generateInt(annotation.minSize(), annotation.maxSize()),
                    annotation.isUnique(),
                    clazz,
                    annotation.randomizeNull());
            } else if (field.isAnnotationPresent(RandomizeString.class)) {
                RandomizeString annotation = field.getAnnotation(RandomizeString.class);
                setNewValue(object,
                    field,
                    () -> stringBehavior(annotation),
                    NumberGenerator.generateInt(annotation.minSize(), annotation.maxSize()),
                    annotation.isUnique(),
                    clazz,
                    annotation.randomizeNull());
            } else if (field.isAnnotationPresent(RandomizeFile.class)) {
                RandomizeFile annotation = field.getAnnotation(RandomizeFile.class);
                setNewValue(object,
                    field,
                    () -> fileBehavior(annotation, object, field),
                    NumberGenerator.generateInt(annotation.minSize(), annotation.maxSize()),
                    false,
                    clazz,
                    false);
            } else if (field.isAnnotationPresent(RandomizeEnum.class)) {
                RandomizeEnum annotation = field.getAnnotation(RandomizeEnum.class);
                setNewValue(object,
                    field,
                    () -> enumBehavior(field),
                    0,
                    false,
                    clazz,
                    annotation.randomizeNull());
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(SelfImplementation.class)) {
                callMethod(object, method);
            }
        }
    }
}
