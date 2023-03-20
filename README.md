# anonymization



## Description

This library have for main purpose to randomize sensitive data using annotation.

## Installation

/!\ à compléter 

## Usage

### Code

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

}
````

````java
public class Main {
    public static void main(String[] args) {
        Person p = new Person();  // create a new Person
        ObjectAnonymizer oa = new ObjectAnonymizer();  // instance of objectAnonymizer 
        Person personAnonimized = (Person) oa.anonymize(p);  // apply anonymization to p
        // note that anonymize return an object which is p modified
        // so p and personAnonymized have the same reference
    }
}
````
### NumberType
<details>
    <summary>
        <b>Available values</b>
    </summary>

value:
- `LONG`
- `INT`
- `FLOAT`
- `DOUBLE`
</details>

### StringType
<details>
    <summary>
        <b>Available values</b>
    </summary>

value:
- `STRING`
- `TEXT`
- `EMAIL`
- `URL`
- `PHONE_INTERNATIONAL`
- `SOCIAL_SECURITY_NUMBER`
- `LICENSE_PLATE`
- `STRING_FROM_FILE`
- `NUMBER`
- `STRING_FROM_ARRAY`
</details>


### RandomizeNumber

<details>
    <summary>
        <b>Parameters</b>
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

minSize and maxSize are used only if the Filed is a collection

</details>

<details>
    <summary>
        <b>Parameters usage by StringType value</b>
    </summary>

|   value | parameters                               | description     |
|--------:|------------------------------------------|-----------------|
|    LONG | minValue, maxValue, minSize, maxSize     | generate long   |
|     INT | minValue, maxValue, minSize, maxSize     | generate int    |
|   FLOAT | minValue, maxValue, minSize, maxSize     | generate float  |
|  DOUBLE | minValue, maxValue, minSize, maxSize     | generate double |

</details>

### RandomizeString

<details>
    <summary>
        <b>Parameters</b>
    </summary>

|           name | type            | is optional | default   | description                               |
|---------------:|-----------------|-------------|-----------|-------------------------------------------|
|          value | StringType      | false       | none      | behavior                                  |
|      minLength | int             | true        | "default" | min length                                |
|      maxLength | int             | true        | "default" | max length                                |
|           path | String          | true        | ""        | path of the file where to get values      |
| possibleValues | Array\<String\> | true        | {}        | array of different values that can be set |
|        minSize | int             | true        | 1         | min size of the collection                |
|        maxSize | int             | true        | 10        | max size of the collection                |

The default minLength (alt. maxLength) value is the minimal (alt. maximal) length possible depending on the StringType,
the final length is selected randomly between minLength and MaxLength.

The size of the collection is selected randomly between minSize and maxSize.

minSize and maxSize are used only if the Filed is a collection

</details>

<details>
    <summary>
        <b>Parameters usage by NumberType value</b>
    </summary>

|                  value | parameters                             | description                                                                               |
|-----------------------:|----------------------------------------|-------------------------------------------------------------------------------------------|
|                 STRING | minLength, maxLength, minSize, maxSize | generate random string, the alphabet is \[a-z\]                                           |
|                   TEXT | minLength, maxLength, minSize, maxSize | generate "Lorem ipsum" text                                                               |
|                  EMAIL | minSize, maxSize                       | generate random email with format : %s.%s@%s.%s                                           |
|                    URL | minSize, maxSize                       | generate random url with format : \[https, http\]://%s/%s/%s                              |
|    PHONE_INTERNATIONAL | minSize, maxSize                       | generate international phone number                                                       |
| SOCIAL_SECURITY_NUMBER | minSize, maxSize                       | generate random social security number with format : \[0,1\]\[0-9\]{2}\[01-12\]\[0-9\]{8} |
|          LICENSE_PLATE | minSize, maxSize                       | generate license plat with format \[A-Z\]{2}-\[0-9\]{3}-\[A-Z\]{2}                        |
|       STRING_FROM_FILE | path, minSize, maxSize                 | select value from file                                                                    |
|      STRING_FROM_ARRAY | possibleValues , minSize, maxSize      | select value from array                                                                   |
|                 NUMBER | minLength, maxLength, minSize, maxSize | generate number as string                                                                 |

replace %s by a random string.

</details>


## Limitation

Table of all handled type

| Single type |
|------------:|
|      STRING |
|        LONG |
|         INT |
|      DOUBLE |
|       FLOAT |

| Collection |
|-----------:|
|       List |
|  ArrayList |
| LinkedList |



## Contributing
/!\ à compléter

## License
/!\ à compléter 

