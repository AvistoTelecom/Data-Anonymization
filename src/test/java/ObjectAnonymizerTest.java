import model.Model;
import model.StringTestModel;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.exception.AnonymeException;
import org.avisto.anonymization.exception.BadUseAnnotationException;
import org.avisto.anonymization.exception.MethodGenerationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        AnonymeException e = Assert.assertThrows(AnonymeException.class, () -> anonymizer.anonymize((StringTestModel) null));
        Assert.assertEquals("The object to anonymize is null", e.getMessage());
    }

    @Test
    public void testIterableObject() {
        Collection<StringTestModel> models = Stream.generate(StringTestModel::new)
                .limit(10)
                .collect(Collectors.toList());
        anonymizer.anonymize(models);
        Assert.assertEquals(10, models.size());
        BadUseAnnotationException e = Assert.assertThrows(BadUseAnnotationException.class, () -> anonymizer.anonymize(null));
        Assert.assertEquals("The object to anonymize is null", e.getMessage());

    }

    @Test
    public void testInaccessibleMethod() {
        Model.ModelWithInaccessibleMethod model = new Model.ModelWithInaccessibleMethod();
        BadUseAnnotationException e = Assert.assertThrows(BadUseAnnotationException.class, () -> anonymizer.anonymize(model));
        Assert.assertEquals("org.avisto.anonymization.exception.BadUseAnnotationException: Method doThing on class class model.Model$ModelWithInaccessibleMethod couldn't be called", e.getMessage());
    }

    @Test
    public void testParamMethod() {
        Model.ModelWithInaccessibleMethod model = new Model.ModelWithInaccessibleMethod();
        BadUseAnnotationException e = Assert.assertThrows(BadUseAnnotationException.class, () -> anonymizer.anonymize(model));
        Assert.assertEquals("org.avisto.anonymization.exception.BadUseAnnotationException: Method doThing on class class model.Model$ModelWithInaccessibleMethod couldn't be called", e.getMessage());
    }
}
