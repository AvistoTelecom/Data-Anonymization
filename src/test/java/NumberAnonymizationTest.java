import model.NumberTestModel;
import model.StringTestModel;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.annotation.Unique;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.generator.NumberGenerator;
import org.avisto.anonymization.model.enums.NumberType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class NumberAnonymizationTest {

    private final ObjectAnonymizer anonymizer = new ObjectAnonymizer();

    private void init(NumberTestModel nb){
        nb.doubleValue = 0d;
        nb.floatValue = 0f;
        nb.intValue = 0;
        nb.longValue = 0L;
        nb.ints = new ArrayList<>();
        nb.longs = new LinkedList<>();
        nb.floats = new HashSet<>();
        nb.doubles = new TreeSet<>();
    }

    @Test
    public void testNullNumberValue(){
        NumberTestModel numberTest = new NumberTestModel();

        anonymizer.anonymize(numberTest);
        Assertions.assertNull(numberTest.doubleValue);
        Assertions.assertNull(numberTest.intValue);
        Assertions.assertNull(numberTest.floatValue);
        Assertions.assertNull(numberTest.longValue);

        Assertions.assertNull(numberTest.ints);
        Assertions.assertNull(numberTest.longs);
        Assertions.assertNull(numberTest.floats);
        Assertions.assertNull(numberTest.doubles);
    }

    @Test
    public void testNotNullNumberValue() {
        NumberTestModel numberTest = new NumberTestModel();
        init(numberTest);
        anonymizer.anonymize(numberTest);

        Assertions.assertNotNull(numberTest.longValue);
        Assertions.assertNotNull(numberTest.intValue);
        Assertions.assertNotNull(numberTest.doubleValue);
        Assertions.assertNotNull(numberTest.floatValue);

        Assertions.assertNotNull(numberTest.longs);
        Assertions.assertNotNull(numberTest.ints);
        Assertions.assertNotNull(numberTest.doubles);
        Assertions.assertNotNull(numberTest.floats);

        Assertions.assertTrue(numberTest.ints.size() >= NumberTestModel.MIN_SIZE && numberTest.ints.size() <= NumberTestModel.MAX_SIZE);
        Assertions.assertTrue(numberTest.floats.size() >= NumberTestModel.MIN_SIZE && numberTest.floats.size() <= NumberTestModel.MAX_SIZE);
        Assertions.assertTrue(numberTest.doubles.size() >= NumberTestModel.MIN_SIZE && numberTest.doubles.size() <= NumberTestModel.MAX_SIZE);
        Assertions.assertTrue(numberTest.longs.size() >= NumberTestModel.MIN_SIZE && numberTest.longs.size() <= NumberTestModel.MAX_SIZE);

    }

    @Test
    public void testBetweenTwoValue() {
        NumberTestModel numberTest = new NumberTestModel();

        anonymizer.anonymize(numberTest);
        init(numberTest);

        Assertions.assertTrue(numberTest.floatValue >= Float.parseFloat(NumberTestModel.STRING_MIN_VALUE) && numberTest.floatValue < Float.parseFloat(NumberTestModel.STRING_MAX_VALUE));
        Assertions.assertTrue(numberTest.intValue >= Integer.parseInt(NumberTestModel.STRING_MIN_VALUE) && numberTest.intValue < Integer.parseInt(NumberTestModel.STRING_MAX_VALUE));
        Assertions.assertTrue(numberTest.longValue >= Long.parseLong(NumberTestModel.STRING_MIN_VALUE) && numberTest.longValue < Long.parseLong(NumberTestModel.STRING_MAX_VALUE));
        Assertions.assertTrue(numberTest.doubleValue >= Double.parseDouble(NumberTestModel.STRING_MIN_VALUE) && numberTest.doubleValue < Double.parseDouble(NumberTestModel.STRING_MAX_VALUE));

        numberTest.floats.forEach(v -> Assertions.assertTrue(v >= Float.parseFloat(NumberTestModel.STRING_MIN_VALUE) && v < Float.parseFloat(NumberTestModel.STRING_MAX_VALUE)));
        numberTest.ints.forEach(v -> Assertions.assertTrue(v >= Integer.parseInt(NumberTestModel.STRING_MIN_VALUE) && v < Integer.parseInt(NumberTestModel.STRING_MAX_VALUE)));
        numberTest.doubles.forEach(v -> Assertions.assertTrue(v >= Double.parseDouble(NumberTestModel.STRING_MIN_VALUE) && v < Double.parseDouble(NumberTestModel.STRING_MAX_VALUE)));
        numberTest.longs.forEach(v -> Assertions.assertTrue(v >= Long.parseLong(NumberTestModel.STRING_MIN_VALUE) && v < Long.parseLong(NumberTestModel.STRING_MAX_VALUE)));
    }

    @Test
    public void testGenerateNumber() {

        Assertions.assertNotNull(NumberGenerator.generateInt());
        Assertions.assertNotNull(NumberGenerator.generateLong());
        Assertions.assertNotNull(NumberGenerator.generateFloat());
        Assertions.assertNotNull(NumberGenerator.generateDouble());
    }

    @Test
    public void testGenerateNumberMinGreaterThanMax() {
        IllegalArgumentException intException = Assertions.assertThrows(IllegalArgumentException.class, () -> NumberGenerator.generateInt(5, 3));
        String minGreaterThanMaxErrorMessage = "max must be greater than min";
        Assertions.assertEquals(minGreaterThanMaxErrorMessage, intException.getMessage());

        IllegalArgumentException longException = Assertions.assertThrows(IllegalArgumentException.class, () -> NumberGenerator.generateLong(5L, 3L));
        Assertions.assertEquals(minGreaterThanMaxErrorMessage, longException.getMessage());

        IllegalArgumentException floatException = Assertions.assertThrows(IllegalArgumentException.class, () -> NumberGenerator.generateFloat(5f, 3f));
        Assertions.assertEquals(minGreaterThanMaxErrorMessage, floatException.getMessage());

        IllegalArgumentException doubleException = Assertions.assertThrows(IllegalArgumentException.class, () -> NumberGenerator.generateDouble(5d, 3d));
        Assertions.assertEquals(minGreaterThanMaxErrorMessage, doubleException.getMessage());
    }

    @Test
    public void testUniqueNumber() {
         NumberTestModel model = new NumberTestModel() {
            @Unique
            @RandomizeNumber(value = NumberType.INT, minValue = "1")
            public Integer intValue;
        };
        anonymizer.anonymize(model);
        anonymizer.anonymize(model);
    }

    @Test
    public void testNumberRandomizeNull() {
        NumberTestModel model = new NumberTestModel() {
            @RandomizeNumber(value = NumberType.INT, randomizeNull = true)
            public Integer intValue;
        };
        model.setIntValue(null);
        anonymizer.anonymize(model);
        Assertions.assertNotNull(model.getIntValue());
    }
}
