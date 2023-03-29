/*
 *
 */

package model;

import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.model.enums.NumberType;

import java.util.Collection;

@Anonyme
public class NumberTestModel {

    public static final String STRING_MIN_VALUE = "0";
    public static final String STRING_MAX_VALUE = "3";
    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 6;


    @RandomizeNumber(value = NumberType.INT, minValue = STRING_MIN_VALUE, maxValue = STRING_MAX_VALUE)
    public Integer intValue;
    @RandomizeNumber(value = NumberType.LONG, minValue = STRING_MIN_VALUE, maxValue = STRING_MAX_VALUE)
    public Long longValue;
    @RandomizeNumber(value = NumberType.FLOAT, minValue = STRING_MIN_VALUE, maxValue = STRING_MAX_VALUE)
    public Float floatValue;
    @RandomizeNumber(value = NumberType.DOUBLE, minValue = STRING_MIN_VALUE, maxValue = STRING_MAX_VALUE)
    public Double doubleValue;


    @RandomizeNumber(value = NumberType.INT, minValue = STRING_MIN_VALUE, maxValue = STRING_MAX_VALUE, minSize = MIN_SIZE, maxSize = MAX_SIZE)
    public Collection<Integer> ints;
    @RandomizeNumber(value = NumberType.LONG, minValue = STRING_MIN_VALUE, maxValue = STRING_MAX_VALUE, minSize = MIN_SIZE, maxSize = MAX_SIZE)
    public Collection<Long> longs;
    @RandomizeNumber(value = NumberType.FLOAT, minValue = STRING_MIN_VALUE, maxValue = STRING_MAX_VALUE, minSize = MIN_SIZE, maxSize = MAX_SIZE)
    public Collection<Float> floats;
    @RandomizeNumber(value = NumberType.DOUBLE, minValue = STRING_MIN_VALUE, maxValue = STRING_MAX_VALUE, minSize = MIN_SIZE, maxSize = MAX_SIZE)
    public Collection<Double> doubles;


    public NumberTestModel() {
    }
}
