import model.MethodTestModel;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SelfImplementationTest {

    public ObjectAnonymizer anonymizer = new ObjectAnonymizer();

    @Test
    public void testCallMethod() {
        MethodTestModel model = new MethodTestModel();
        anonymizer.anonymize(model);
        Assertions.assertEquals("hello world", model.value);
    }
}
