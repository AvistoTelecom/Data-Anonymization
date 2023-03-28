import model.MethodTestModel;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.junit.Assert;
import org.junit.Test;

public class SelfImplementationTest {

    public ObjectAnonymizer anonymizer = new ObjectAnonymizer();

    @Test
    public void testCallMethod() {
        MethodTestModel model = new MethodTestModel();
        anonymizer.anonymize(model);
        Assert.assertEquals("hello world", model.value);
    }
}
