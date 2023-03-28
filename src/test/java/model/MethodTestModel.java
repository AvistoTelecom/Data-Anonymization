/*
 *
 */

package model;

import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.SelfImplementaion;

@Anonyme
public class MethodTestModel {
    public String value;

    @SelfImplementaion
    public void customBehavior() {
        value = "hello world";
    }
}
