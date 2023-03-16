package org.avisto.anonymization.model;

import lombok.*;
import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.model.enums.NumberType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Anonyme
public class Person {

    private String firstName;


    private String lastName;

    private String email;

    private Long age;


    private Boolean haveChildren;

    private List<Double> salary;


}
