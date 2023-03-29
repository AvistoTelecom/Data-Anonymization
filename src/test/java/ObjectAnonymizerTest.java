import model.Model;
import model.StringTestModel;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.exception.AnonymeException;
import org.avisto.anonymization.exception.BadUseAnnotationException;
import org.avisto.anonymization.exception.MethodGenerationException;
import org.junit.Assert;
import org.junit.Test;

public class ObjectAnonymizerTest {
    public ObjectAnonymizer anonymizer = new ObjectAnonymizer();

    @Test
    public void testWrongAnnotation() {
        Model.ModelWithWrongAnnotation model = new Model.ModelWithWrongAnnotation();
        BadUseAnnotationException e = Assert.assertThrows(BadUseAnnotationException.class, () -> anonymizer.anonymize(model));
        Assert.assertEquals("java.lang.IllegalArgumentException: argument type mismatch", e.getMessage());
    }

    @Test
    public void testWithoutAnonyme() {
        Model.ModelWithoutAnonyme model = new Model.ModelWithoutAnonyme();
        AnonymeException e = Assert.assertThrows(AnonymeException.class, () -> anonymizer.anonymize(model));
        Assert.assertEquals("The class ModelWithoutAnonyme is not annotated with Anonyme", e.getMessage());
    }

    @Test
    public void testNoGetter() {
        Model.ModelWithNoGetter model = new Model.ModelWithNoGetter();
        MethodGenerationException e = Assert.assertThrows(MethodGenerationException.class, () -> anonymizer.anonymize(model));
        Assert.assertEquals(String.format("failed to call getter method of the field : 'public java.lang.String model.Model$ModelWithNoGetter.value'%n" +
                "please be sure that there is a getter available for this field and is named get<Fieldname> with a no parameter"), e.getMessage());
    }

    @Test
    public void testNoSetter() {
        Model.ModelWithNoSetter model = new Model.ModelWithNoSetter();
        MethodGenerationException e = Assert.assertThrows(MethodGenerationException.class, () -> anonymizer.anonymize(model));
        Assert.assertEquals(String.format("failed to call setter method of the field : 'public java.lang.String model.Model$ModelWithNoSetter.value'%n" +
                "please be sure that there is a setter available for this field and is named set<Fieldname> with a unique parameter that the type is the same as the field"),
                e.getMessage());
    }

    @Test
    public void testNullObject() {
        StringTestModel model = null;
        AnonymeException e = Assert.assertThrows(AnonymeException.class, () -> anonymizer.anonymize(model));
        Assert.assertEquals("The object to anonymize is null", e.getMessage());
    }
}
