package org.avisto.anonymization;

import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.generator.StringGenerator;
import org.avisto.anonymization.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Object p = new Person();
        ObjectAnonymizer oa = new ObjectAnonymizer();
        ArrayList<Person> people = new ArrayList<>();
        System.out.println(oa.anonymize(p));
//        System.out.println(Iterable.class.isAssignableFrom(LinkedList.class));

    }
}
