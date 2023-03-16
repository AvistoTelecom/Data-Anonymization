package org.avisto.anonymization.model;

import lombok.*;
import org.avisto.anonymization.annotation.Anonyme;
import org.avisto.anonymization.annotation.RandomizeNumber;
import org.avisto.anonymization.annotation.RandomizeString;
import org.avisto.anonymization.model.enums.NumberType;
import org.avisto.anonymization.model.enums.StringType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Anonyme
public class Person {

    @RandomizeString(StringType.STRING)
    private String firstName;

    @RandomizeString(value = StringType.STRING, minLength = 2, maxLength = 2)
    private String lastName;

    @RandomizeString(StringType.EMAIL)
    private String email;

    @RandomizeString(StringType.PHONE_INTERNATIONAL)
    private String phone;

    @RandomizeString(StringType.LICENSE_PLATE)
    private String licensePlate;

    @RandomizeString(StringType.URL)
    private String url;

    @RandomizeString(StringType.NUMBER)
    private String number;

    @RandomizeString(value = StringType.STRING_FROM_FILE, path = "src/main/resources/data_test.txt")
    private String file;

    @RandomizeString(value = StringType.STRING_FROM_ARRAY, possibleValues = {"hello", "world", "I", "am", "new"})
    private String array;

    @RandomizeNumber(NumberType.LONG)
    private Long age;

}
