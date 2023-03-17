package org.avisto.anonymization;

import lombok.extern.log4j.Log4j2;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.model.Person;

@Log4j2
public class Main {
    public static void main(String[] args) {
        Object p = new Person();
        ObjectAnonymizer oa = new ObjectAnonymizer();
        log.info(oa.anonymize(p));

    }
}
