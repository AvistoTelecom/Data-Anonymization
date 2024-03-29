package model;

import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeString;
import org.avisto.anonymization.model.enums.StringType;

@Anonyme
public class StringTestModel {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 5;
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 3;
    public static final String PATH_TO_FILE = "src/test/resources/dataTest.txt";
    public static final String PATTERN = "[a-z]{4}";

    public String value;

    public StringTestModel(String value) {
        this.value = value;
    }

    public StringTestModel() {
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
