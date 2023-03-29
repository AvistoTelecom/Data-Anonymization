import model.StringTestModel;
import org.avisto.anonymization.annotation.RandomizeString;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.StringType;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class StringAnonymizationTest {
    public ObjectAnonymizer anonymizer = new ObjectAnonymizer();

    public void init(StringTestModel model){
        model.value = "";
    }

    @Test
    public void testNullValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(StringType.STRING)
            public String value;
        };
        anonymizer.anonymize(model);
        Assert.assertNull(model.value);

    }

    @Test
    public void testStringValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.STRING, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(model.value.length() >= StringTestModel.MIN_LENGTH && model.value.length() <= StringTestModel.MAX_LENGTH);

    }

    @Test
    public void testTextValue() {
        String lorem = StringGenerator.LOREM_IPSUM;
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.TEXT, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertEquals(model.value, lorem.substring(0, model.value.length()));
    }

    @Test
    public void testIpv6Value() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.IPV6, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches("([0-9a-f]{0,4}:){7}([0-9a-f]{0,4})", model.value));
    }

    @Test
    public void testIpv4Value() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.IPV4, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)", model.value));
    }

    @Test
    public void testRegexValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.REGEX, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches("[a-z]{4}", model.value));
    }

    @Test
    public void testEmailValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.EMAIL, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches(".*@.*[.].*", model.value));
    }

    @Test
    public void testStringFomFileValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.STRING_FROM_FILE, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertEquals("first value", model.value);
    }

    @Test
    public void testStringFomArrayValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.STRING_FROM_ARRAY, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertEquals("first", model.value);
    }

    @Test
    public void testStringFomArrayValueEmpty() {
        IllegalArgumentException e = Assert.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_ARRAY.getRandomValue(0,0,null, new String[]{}, null));
        Assert.assertEquals("On STRING_FROM_ARRAY type, possibleValues must not be empty or null", e.getMessage());

        e = Assert.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_ARRAY.getRandomValue(0,0,null, null, null));
        Assert.assertEquals("On STRING_FROM_ARRAY type, possibleValues must not be empty or null", e.getMessage());
    }

    @Test
    public void testStringFomFileValueEmpty() {
        IllegalArgumentException e = Assert.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_FILE.getRandomValue(0,0,"  ", null, null));
        Assert.assertEquals("On STRING_FROM_FILE type, path must not be blank or null", e.getMessage());

        e = Assert.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_FILE.getRandomValue(0,0,null, null, null));
        Assert.assertEquals("On STRING_FROM_FILE type, path must not be blank or null", e.getMessage());

        e = Assert.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_FILE.getRandomValue(0,0,"", null, null));
        Assert.assertEquals("On STRING_FROM_FILE type, path must not be blank or null", e.getMessage());
    }

    @Test
    public void testNumberValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.NUMBER, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        String pattern = String.format("[0-9]{%s,%s}", StringTestModel.MIN_LENGTH, StringTestModel.MAX_LENGTH);
        Assert.assertTrue(Pattern.matches(pattern, model.value));
    }

    @Test
    public void testUrlValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.URL, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches("(http|https)://.*", model.value));
    }

    @Test
    public void testPhoneInternationalValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.PHONE_INTERNATIONAL, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches("[0-9]{13}", model.value));
    }

    @Test
    public void testPhoneFrValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.PHONE_FR, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches("(06|07)[0-9]{8}", model.value));
    }

    @Test
    public void testLicensePlateValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.LICENSE_PLATE, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches("[A-Z]{2}-[0-9]{3}-[A-Z]{2}", model.value));
    }

    @Test
    public void testSocialSecurityNumberValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.SOCIAL_SECURITY_NUMBER, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assert.assertTrue(Pattern.matches("[12][0-9]{2}(1[012]|0[1-9])[0-9]{8}", model.value));
    }
    @Test
    public void testNotEnoughNumberDigit() {
        IllegalArgumentException e = Assert.assertThrows(IllegalArgumentException.class, () -> StringGenerator.generateNumber(20, 25, 1));
        Assert.assertEquals("not enough number of digits to represent max value", e.getMessage());
    }

    @Test
    public void testValueFromFile_FileNotFound() {
        IllegalArgumentException e = Assert.assertThrows(IllegalArgumentException.class, () -> StringGenerator.generateStringFromFile("PATH/TO/FILE.txt"));
        Assert.assertEquals("file PATH/TO/FILE.txt not found", e.getMessage());
    }
}
