import model.NumberTestModel;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertNull(numberTest.doubleValue);
        Assert.assertNull(numberTest.intValue);
        Assert.assertNull(numberTest.floatValue);
        Assert.assertNull(numberTest.longValue);

        Assert.assertNull(numberTest.ints);
        Assert.assertNull(numberTest.longs);
        Assert.assertNull(numberTest.floats);
        Assert.assertNull(numberTest.doubles);


    }

    @Test
    public void testNotNullNumberValue() {
        NumberTestModel numberTest = new NumberTestModel();
        init(numberTest);
        anonymizer.anonymize(numberTest);

        Assert.assertNotNull(numberTest.longValue);
        Assert.assertNotNull(numberTest.intValue);
        Assert.assertNotNull(numberTest.doubleValue);
        Assert.assertNotNull(numberTest.floatValue);

        Assert.assertNotNull(numberTest.longs);
        Assert.assertNotNull(numberTest.ints);
        Assert.assertNotNull(numberTest.doubles);
        Assert.assertNotNull(numberTest.floats);

        Assert.assertTrue(numberTest.ints.size() >= NumberTestModel.MIN_SIZE && numberTest.ints.size() <= NumberTestModel.MAX_SIZE);
        Assert.assertTrue(numberTest.floats.size() >= NumberTestModel.MIN_SIZE && numberTest.floats.size() <= NumberTestModel.MAX_SIZE);
        Assert.assertTrue(numberTest.doubles.size() >= NumberTestModel.MIN_SIZE && numberTest.doubles.size() <= NumberTestModel.MAX_SIZE);
        Assert.assertTrue(numberTest.longs.size() >= NumberTestModel.MIN_SIZE && numberTest.longs.size() <= NumberTestModel.MAX_SIZE);

    }

    @Test
    public void testBetweenTwoValue() {
        NumberTestModel numberTest = new NumberTestModel();

        anonymizer.anonymize(numberTest);
        init(numberTest);

        Assert.assertTrue(numberTest.floatValue >= Float.parseFloat(NumberTestModel.STRING_MIN_VALUE) && numberTest.floatValue < Float.parseFloat(NumberTestModel.STRING_MAX_VALUE));
        Assert.assertTrue(numberTest.intValue >= Integer.parseInt(NumberTestModel.STRING_MIN_VALUE) && numberTest.intValue < Integer.parseInt(NumberTestModel.STRING_MAX_VALUE));
        Assert.assertTrue(numberTest.longValue >= Long.parseLong(NumberTestModel.STRING_MIN_VALUE) && numberTest.longValue < Long.parseLong(NumberTestModel.STRING_MAX_VALUE));
        Assert.assertTrue(numberTest.doubleValue >= Double.parseDouble(NumberTestModel.STRING_MIN_VALUE) && numberTest.doubleValue < Double.parseDouble(NumberTestModel.STRING_MAX_VALUE));

        numberTest.floats.forEach(v -> Assert.assertTrue(v >= Float.parseFloat(NumberTestModel.STRING_MIN_VALUE) && v < Float.parseFloat(NumberTestModel.STRING_MAX_VALUE)));
        numberTest.ints.forEach(v -> Assert.assertTrue(v >= Integer.parseInt(NumberTestModel.STRING_MIN_VALUE) && v < Integer.parseInt(NumberTestModel.STRING_MAX_VALUE)));
        numberTest.doubles.forEach(v -> Assert.assertTrue(v >= Double.parseDouble(NumberTestModel.STRING_MIN_VALUE) && v < Double.parseDouble(NumberTestModel.STRING_MAX_VALUE)));
        numberTest.longs.forEach(v -> Assert.assertTrue(v >= Long.parseLong(NumberTestModel.STRING_MIN_VALUE) && v < Long.parseLong(NumberTestModel.STRING_MAX_VALUE)));
    }
}
