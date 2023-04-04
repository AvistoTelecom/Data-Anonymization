package model;

import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.SelfImplementation;

@Anonyme
public class MethodTestModel {
    public String value;

    @SelfImplementation
    public void customBehavior() {
        value = "hello world";
    }
}
