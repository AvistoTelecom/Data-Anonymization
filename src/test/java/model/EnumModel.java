/*
 *
 */

package model;

import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeEnum;
import org.avisto.anonymization.model.enums.NumberType;

public class EnumModel {

    @Anonyme
    public static class EnumWrongType {

        @RandomizeEnum
        public String value = "wrong type !";

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Anonyme
    public static class EnumWellFormated {

        @RandomizeEnum
        public NumberType value = NumberType.INT;

        public NumberType getValue() {
            return value;
        }

        public void setValue(NumberType value) {
            this.value = value;
        }
    }
}
