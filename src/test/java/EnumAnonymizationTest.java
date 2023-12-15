/*
 *
 */

import model.EnumModel;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.exception.BadUseAnnotationException;
import org.avisto.anonymization.model.enums.NumberType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class EnumAnonymizationTest {
    ObjectAnonymizer anonymizer = new ObjectAnonymizer();

    @Test
    public void enumWrongTypeTest() {
        EnumModel.EnumWrongType model = new EnumModel.EnumWrongType();
        BadUseAnnotationException e = Assertions.assertThrows(BadUseAnnotationException.class, () -> anonymizer.anonymize(model));
        Assertions.assertEquals("field should be enumeration", e.getMessage());
    }

    @Test
    public void enumWellConstructTest() {
        EnumModel.EnumWellFormated model = new EnumModel.EnumWellFormated();
        anonymizer.anonymize(model);
        Assertions.assertTrue(Arrays.stream(NumberType.values()).anyMatch( enumValue -> model.value.equals(enumValue) ));
    }
}
