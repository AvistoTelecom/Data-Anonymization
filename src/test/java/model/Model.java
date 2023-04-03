package model;

import org.avisto.anonymization.annotation.*;
import org.avisto.anonymization.model.enums.NumberType;
import org.avisto.anonymization.model.enums.StringType;

public class Model {

    public static class ModelWithoutAnonyme {
    }
    @Anonyme
    public static class ModelWithWrongAnnotation {
        @RandomizeNumber(NumberType.LONG)
        public String value;
        public void setValue(String value) { this.value = value; }
        public String getValue() { return value; }
        public ModelWithWrongAnnotation(){
            value = "hello";
        }
    }

    @Anonyme
    public static class ModelWithNoGetter {
        @RandomizeString(StringType.STRING)
        public String value;
        public void setValue(String value) { this.value = value; }
        public ModelWithNoGetter(){
            value = "hello";
        }
    }

    @Anonyme
    public static class ModelWithNoSetter {
        @RandomizeString(StringType.STRING)
        public String value;
        public String getValue() { return value; }
        public ModelWithNoSetter(){
            value = "hello";
        }
    }

    @Anonyme
    public static class ModelWithPrivateGetter {
        @RandomizeString(StringType.STRING)
        public String value;
        private String getValue() { return value; }
        public ModelWithPrivateGetter(){
            value = "hello";
        }
    }

    @Anonyme
    public static class ModelWithPrivateSetter {
        @RandomizeString(StringType.STRING)
        public String value;
        public String getValue() { return value; }

        private void setValue(String value) { this.value = value; }
        public ModelWithPrivateSetter(){
            value = "hello";
        }
    }

    @Anonyme
    public static class ModelWithInaccessibleMethod {
        @RandomizeString(StringType.STRING)
        public String value;
        public String getValue() { return value; }

        public void setValue(String value) { this.value = value; }
        public ModelWithInaccessibleMethod(){
            value =  "";
        }

        @SelfImplementation
        private void doThing() {
            value = "do thing";
        }
    }

    @Anonyme
    public static class ModelWithParamMethod {
        @RandomizeString(StringType.STRING)
        public String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public ModelWithParamMethod() {
            value = "";
        }

        @SelfImplementation
        private void doThing(String thing) {
            value = thing;
        }
    }

    @Anonyme
    public static class ModelWithOldFileRemove {
        @RandomizeFile(pathToDirectory = "src/test/resources/file/new/", removeOld = false, nameFileBehavior = @RandomizeString(value = StringType.STRING_FROM_ARRAY, possibleValues = {"newFile"}))
        public String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public ModelWithOldFileRemove() {
            value = "src/test/resources/file/new/file.txt";
        }
    }

    @Anonyme
    public static class ModelWithFile {
        @RandomizeFile(pathToDirectory = "src/test/resources/file/new/", nameFileBehavior = @RandomizeString(value = StringType.STRING_FROM_ARRAY, possibleValues = {"newFile"}))
        public String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public ModelWithFile() {
            value = "src/test/resources/file/new/file.txt";
        }
    }
}
