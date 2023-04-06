# anonymization



## Description

This library have for main purpose to randomize sensitive data using annotation.

## Installation

/!\ à compléter 

## Usage

### Code

To allow the ObjectAnonymizer to anonymize your Object, the Class must be annotated with **@Anonyme**.

Then all field that need to be anonymized must be annotated with annotation depending on the field type.

following an example of how to use the library.

````java

@Anonyme
public class Person {

    @RandomizeString(StringType.STRING)
    private String firstName;

    @RandomizeString(value = StringType.STRING, minLength = 2, maxLength = 2)
    private String lastName;

    @RandomizeString(StringType.EMAIL)
    private String email;
    
    @RandomizeString(StringType.NUMBER)
    private String number;

    @RandomizeNumber(value = NumberType.LONG, minValue = "15", maxValue = "21", minSize = 3, maxSize = 6)
    private List<Long> longs;

    /*  Getter and Setter necessary */
    /* Constructor */
}
````

````java
import org.avisto.anonymization.Person;

import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Person p = new Person();  // create a new Person
        ObjectAnonymizer oa = new ObjectAnonymizer();  // instance of objectAnonymizer 
        oa.anonymize(p);  // apply anonymization to p

        List<Person> listPerson = Stream.generate(Person::new).limit(5).collect(Collectors.toList());
        oa.anonymize(listPerson); // apply anonymization to all person in the list
    }
}
````

## Enum

### NumberType
**Enum that represent all handling number type generator.**
<details>
    <summary>
        Available values
    </summary>

value:
- `LONG`
- `INT`
- `FLOAT`
- `DOUBLE`
</details>

### StringType
**Enum that represent all handling StringFormat generator.**

<details>
    <summary>
        Available values
    </summary>

value:
- `STRING`
- `TEXT`
- `EMAIL`
- `URL`
- `PHONE_INTERNATIONAL`
- `PHONE_FR`
- `SOCIAL_SECURITY_NUMBER`
- `LICENSE_PLATE`
- `STRING_FROM_FILE`
- `NUMBER`
- `STRING_FROM_ARRAY`
- `REGEX`
</details>

## Annotation

### RandomizeNumber

<details>
    <summary>
        Parameters
    </summary>

|     name | type       | is optional | default   | description                |
|---------:|------------|-------------|-----------|----------------------------|
|    value | NumberType | false       | none      | behavior                   |
| minValue | String     | true        | "default" | min value                  |
| maxValue | String     | true        | "default" | max value                  |
|  minSize | int        | true        | 1         | min size of the collection |
|  maxSize | int        | true        | 15        | max size of the collection |

The default min (alt. max) value is the minimal (alt. maximal) value possible depending on the numberType.

The size of the collection is selected randomly between minSize and maxSize.

minSize and maxSize are used only if the Filed is a collection.

</details>

<details>
    <summary>
        Parameters usage by NumberType value
    </summary>

|   value | parameters                               | description     |
|--------:|------------------------------------------|-----------------|
|    LONG | minValue, maxValue, minSize, maxSize     | generate long   |
|     INT | minValue, maxValue, minSize, maxSize     | generate int    |
|   FLOAT | minValue, maxValue, minSize, maxSize     | generate float  |
|  DOUBLE | minValue, maxValue, minSize, maxSize     | generate double |

</details>

### RandomizeString <a id='stringAnnotation'/>

<details>
    <summary>
        Parameters
    </summary>

|           name | type            | is optional | default         | description                               |
|---------------:|-----------------|-------------|-----------------|-------------------------------------------|
|          value | StringType      | false       | none            | behavior                                  |
|      minLength | int             | true        | "default"       | min length                                |
|      maxLength | int             | true        | "default"       | max length                                |
|           path | String          | true        | ""              | path of the file where to get values      |
| possibleValues | Array\<String\> | true        | {}              | array of different values that can be set |
|        minSize | int             | true        | 1               | min size of the collection                |
|        maxSize | int             | true        | 10              | max size of the collection                |
|        pattern | String          | true        | "\[a-z\]{5,12}" | regex pattern                             |

The default minLength (alt. maxLength) value is the minimal (alt. maximal) length possible depending on the StringType,
the final length is selected randomly between minLength and MaxLength.

The size of the collection is selected randomly between minSize and maxSize.

minSize and maxSize are used only if the Field is a collection.

see supported regex pattern syntax [here](https://github.com/curious-odd-man/RgxGen#supported-syntax).

</details>

<details>
    <summary>
        Parameters usage by StringType value
    </summary>

|                  value | parameters                             | description                                                                               |
|-----------------------:|----------------------------------------|-------------------------------------------------------------------------------------------|
|                 STRING | minLength, maxLength, minSize, maxSize | generate random string, the alphabet is \[a-z\]                                           |
|                   TEXT | minLength, maxLength, minSize, maxSize | generate "Lorem ipsum" text                                                               |
|                  EMAIL | minSize, maxSize                       | generate random email with format : %s.%s@%s.%s                                           |
|                    URL | minSize, maxSize                       | generate random url with format : \[https, http\]://%s/%s/%s                              |
|    PHONE_INTERNATIONAL | minSize, maxSize                       | generate international phone number                                                       |
|               PHONE_FR | minSize, maxSize                       | generate french national phone number                                                     |
| SOCIAL_SECURITY_NUMBER | minSize, maxSize                       | generate random social security number with format : \[0,1\]\[0-9\]{2}\[01-12\]\[0-9\]{8} |
|          LICENSE_PLATE | minSize, maxSize                       | generate license plat with format \[A-Z\]{2}-\[0-9\]{3}-\[A-Z\]{2}                        |
|       STRING_FROM_FILE | path, minSize, maxSize                 | select value from file                                                                    |
|      STRING_FROM_ARRAY | possibleValues , minSize, maxSize      | select value from array                                                                   |
|                 NUMBER | minLength, maxLength, minSize, maxSize | generate number as string                                                                 |
|                  REGEX | pattern, minSize, maxSize              | generate string which respect the pattern                                                 |
|                   IPV4 | minSize, maxSize                       | generate string which respect IPV4 format                                                 |
|                   IPV6 | minSize, maxSize                       | generate string which respect IPV6 format                                                 |

replace %s by a random string.

</details>


### RandomizeFile

<details>
    <summary>
        Parameters
    </summary>

|             name | type             | is optional | default                                                                     | description                                   |
|-----------------:|------------------|-------------|-----------------------------------------------------------------------------|-----------------------------------------------|
|  pathToDirectory | String           | false       | none                                                                        | directory where to save new file              |
| nameFileBehavior | @RandomizeString | true        | @RandomizeString(value = StringType.STRING, minLength = 15, maxLength = 30) | behavior how to generate the name of new file |
|        removeOld | boolean          | true        | true                                                                        | define if the old file should be removed      |
|          minSize | int              | true        | 1                                                                           | min size of the collection                    |
|          maxSize | int              | true        | 15                                                                          | max size of the collection                    |

The size of the collection is selected randomly between minSize and maxSize.

See more about nameFileBehavior on [RandomizeString](#stringAnnotation)

</details>

<details>
    <summary>
        Handle format
    </summary>

|    format    | extension                                   |
|:------------:|---------------------------------------------|
|    image     | `bmp` `gif` `png` `jpg` `jpeg` `tiff` `svg` |
|     text     | `odt` `docx` `txt`                          |
| presentation | `ods` `pptx`                                |
| spreadsheet  | `odp` `xlsx` `csv`                          |
|    video     | `mp4`                                       |
|    audio     | `mp3` `m4a` `flac` `ogg` `wav`              |
|    other     | `pdf` `html` `html`                         |


</details>

### RandomizeEnum

set a random value that the Enum have.

Doesn't work on collection of Enum yet.

### SelfImplementation

All method that are annotated with @SelfImplementation are called after randomizing attribute. Method must have no parameter and be public.

This can be used if you want to make custom behavior on some field.


## Generator

All method used in the library except for regex use these classes to generate different values.

|    class name    |
|:----------------:|
| StringGenerator  |
| NumberGenerator  |
| BooleanGenerator |
|  FileGenerator   |
|    Generator     |

See more about these classes and method available see [javadoc](http://localhost:63342/anonymization/target/site/apidocs/org/avisto/anonymization/generator/package-tree.html)

javadoc is locally generated by the plugin


## Limitation

**Table of all handled type**

| Single type |
|------------:|
|      STRING |
|        LONG |
|         INT |
|      DOUBLE |
|       FLOAT |
|        ENUM |

| Collection |
|-----------:|
|        all |

The regex generation is based on library [RgxGen version 1.4](https://github.com/curious-odd-man/RgxGen/tree/1.4)

Getter and Setter must be declared with a name format: setFieldName, getFieldName. 

This library work with default getter and setter build with lombok

If a field has null value it will stay null

</details>



## Contributing
/!\ TO COMPLETE

## License
/!\ TO COMPLETE 

