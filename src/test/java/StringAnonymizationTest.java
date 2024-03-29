import model.StringTestModel;
import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeString;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.exception.UniqueException;
import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.enums.StringType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertNull(model.value);

    }

    @Test
    public void testStringValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.STRING, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(model.value.length() >= StringTestModel.MIN_LENGTH && model.value.length() <= StringTestModel.MAX_LENGTH);

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
        Assertions.assertEquals(model.value, lorem.substring(0, model.value.length()));
    }

    @Test
    public void testIpv6Value() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.IPV6, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches("([0-9a-f]{0,4}:){7}([0-9a-f]{0,4})", model.value));
    }

    @Test
    public void testIpv4Value() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.IPV4, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)", model.value));
    }

    @Test
    public void testRegexValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.REGEX, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches("[a-z]{4}", model.value));
    }

    @Test
    public void testEmailValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.EMAIL, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches(".*@.*[.].*", model.value));
    }

    @Test
    public void testStringFomFileValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.STRING_FROM_FILE, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertEquals("first value", model.value);
    }

    @Test
    public void testStringFomArrayValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.STRING_FROM_ARRAY, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertEquals("first", model.value);
    }

    @Test
    public void testStringFomArrayValueEmpty() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_ARRAY.getRandomValue(0,0,null, new String[]{}, null));
        Assertions.assertEquals("On STRING_FROM_ARRAY type, possibleValues must not be empty or null", e.getMessage());

        e = Assertions.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_ARRAY.getRandomValue(0,0,null, null, null));
        Assertions.assertEquals("On STRING_FROM_ARRAY type, possibleValues must not be empty or null", e.getMessage());
    }

    @Test
    public void testStringFomFileValueEmpty() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_FILE.getRandomValue(0,0,"  ", null, null));
        Assertions.assertEquals("On STRING_FROM_FILE type, path must not be blank or null", e.getMessage());

        e = Assertions.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_FILE.getRandomValue(0,0,null, null, null));
        Assertions.assertEquals("On STRING_FROM_FILE type, path must not be blank or null", e.getMessage());

        e = Assertions.assertThrows(IllegalArgumentException.class, () -> StringType.STRING_FROM_FILE.getRandomValue(0,0,"", null, null));
        Assertions.assertEquals("On STRING_FROM_FILE type, path must not be blank or null", e.getMessage());
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
        Assertions.assertTrue(Pattern.matches(pattern, model.value));
    }

    @Test
    public void testUrlValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.URL, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches("(http|https)://.*", model.value));
    }

    @Test
    public void testPhoneInternationalValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.PHONE_INTERNATIONAL, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches("[0-9]{13}", model.value));
    }

    @Test
    public void testPhoneFrValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.PHONE_FR, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches("(06|07)[0-9]{8}", model.value));
    }

    @Test
    public void testLicensePlateValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.LICENSE_PLATE, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches("[A-Z]{2}-[0-9]{3}-[A-Z]{2}", model.value));
    }

    @Test
    public void testSocialSecurityNumberValue() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.SOCIAL_SECURITY_NUMBER, maxLength = StringTestModel.MAX_LENGTH, minLength = StringTestModel.MIN_LENGTH, minSize = StringTestModel.MIN_SIZE, maxSize = StringTestModel.MAX_SIZE, path = StringTestModel.PATH_TO_FILE, possibleValues = {"first"}, pattern = StringTestModel.PATTERN)
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        Assertions.assertTrue(Pattern.matches("[12][0-9]{2}(1[012]|0[1-9])[0-9]{8}", model.value));
    }
    @Test
    public void testNotEnoughNumberDigit() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> StringGenerator.generateNumber(20, 25, 1));
        Assertions.assertEquals("not enough number of digits to represent max value", e.getMessage());
    }

    @Test
    public void testValueFromFile_FileNotFound() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> StringGenerator.generateStringFromFile("PATH/TO/FILE.txt"));
        Assertions.assertEquals("file PATH/TO/FILE.txt not found", e.getMessage());
    }

    @Test
    public void testNumberGenerator() {
        String n = StringGenerator.generateNumber(1, 1, 2);
        Assertions.assertEquals("01", n);
    }

    @Test
    public void testTextGenerator() {
        String n = StringGenerator.generateText(5, 5);
        Assertions.assertEquals("Lorem", n);
    }

    @Test
    public void testRegexGenerator() {
        String n = StringGenerator.generateFromRegex("[a-z]{2,3}[0-9]{10}");
        Assertions.assertTrue(Pattern.matches("[a-z]{2,3}[0-9]{10}", n));
    }

    @Test
    public void testUniqueString() {
        StringTestModel model = new StringTestModel() {
            @RandomizeString(value = StringType.REGEX, maxLength = 1, minLength = 1, isUnique = true, pattern = "[a-b]")
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        String firstValue = model.getValue();
        anonymizer.anonymize(model);
        Assertions.assertNotEquals(model.getValue(), firstValue);
    }

    @Test
    public void testUniqueStringFail() {
        StringTestModel model = new StringTestModel() {
            @RandomizeString(value = StringType.REGEX, maxLength = 1, minLength = 1, isUnique = true, pattern = "[a-a]")
            public String value;
        };
        init(model);
        anonymizer.anonymize(model);
        UniqueException e = Assertions.assertThrows(UniqueException.class, () -> anonymizer.anonymize(model));
        Assertions.assertTrue(e.getMessage().contains("Can not anonymize this field who contain a unique key :"));
    }

    @Test
    public void testStringRandomizeNull() {
        StringTestModel model = new StringTestModel(){
            @RandomizeString(value = StringType.STRING, randomizeNull = true)
            public String value;
        };
        model.setValue(null);
        anonymizer.anonymize(model);
        Assertions.assertNotNull(model.getValue());
    }

    @Anonyme
    public static class A {
        @RandomizeString(value = StringType.STRING)
        protected String a = "clear";

        public String getA() { return a; }
        public void setA(String a) { this.a = a; }
    }

    @Anonyme
    public static class B extends A {
        @RandomizeString(value = StringType.STRING)
        private String b = "clear";
        public String getB() { return b; }
        public void setB(String b) { this.b = b; }
    }

    @Test
    public void testAnonymiseParent() {
        ObjectAnonymizer oa = new ObjectAnonymizer();
        B b = new B();
        oa.anonymize(b);
        Assertions.assertNotEquals(b.getB(), "clear");
        Assertions.assertNotEquals(b.getA(), "clear");
    }

}
