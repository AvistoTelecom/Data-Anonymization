# Anonymization

## Description

This document depicts the sensitive data randomization library.
For this purpose, this library gives several tools to achieve anonymization.

Below, the plan:
1. Using annotation
2. Using available utility class

The first one is by using annotation, the second one is by using utility class available.

## Installation

/!\ à compléter 

## Usage
To allow the **ObjectAnonymizer** to anonymize your Object, the Class must be annotated with @Anonyme.

Then all fields that need to be anonymized must have the annotation depending on the field type.

If a field is null, then it will let the value to null.

Below, few examples.

### Examples

#### Basic use
<details>
    <summary>
        <i>Simple example using only annotation</i>
    </summary>

In this example, we want to randomize value.
First we set annotation **@Anonyme** on the class, then we annotate fields that need to be randomized.

````java
@Anonyme
public class Person {

    @RandomizeString(StringType.STRING)
    private String firstName = "";

    @RandomizeString(value = StringType.STRING, minLength = 2, maxLength = 2)
    private String lastName = "";

    @RandomizeString(StringType.EMAIL)
    private String email = "";

    @RandomizeString(StringType.NUMBER)
    private String number = "";

    @RandomizeNumber(value = NumberType.LONG, minValue = "15", maxValue = "21", minSize = 3, maxSize = 6)
    private List<Long> longs = new ArrayList<>();

    /*  Getter and Setter necessary */
    /* Constructor */
}
````

````java
public class Main {
    public static void main(String[] args) {
        Person p = new Person();  // create a new Person
        ObjectAnonymizer oa = new ObjectAnonymizer();  // instance of objectAnonymizer 
        oa.anonymize(p);  // apply anonymization to p
    }
}
````

    Output: 
    p -> { firstName : adispmd, lastName : zr, email : azrfsq.gfzeryda@gyfdg.ftd, number : 791310314, longs : [16, 20, 21, 19] }

Here, all the annotated fields will be randomized based on the behavior given in the annotation.

</details>

<details>
    <summary>
        <i>Example with a custom implementation</i>
    </summary>

In this example, we want to randomize **firstName** and **lastName** using annotation. We want **fullName** as **firstName** and **lastName** anonymized separated by a space.

We can use **@SelfImplementation** as following to consider other fields during the anonymization.

**First**, we set annotation **@Anonyme** on the class, then we annotate fields **firstName** and **lastName**.
At last, we create a method that will be annotated with **@SelfImplementation**.

A method annotated with **@SelfImplementation** will be called after field anonymization.



````java
@Anonyme
public class Person {

    @RandomizeString(StringType.STRING)
    private String firstName = "";

    @RandomizeString(value = StringType.STRING, minLength = 2, maxLength = 2)
    private String lastName = "";

    private String fullName = "";

    @SelfImplementation
    public void customExample() {
        fullName = firstName + " " + lastName;
    }

    /*  Getter and Setter necessary */
    /* Constructor */
}
````

````java
public class Main {
    public static void main(String[] args) {
        Person p = new Person();  // create a new Person
        ObjectAnonymizer oa = new ObjectAnonymizer();  // instance of objectAnonymizer 
        oa.anonymize(p);  // apply anonymization to p
    }
}
````

    Output: 
    p -> { firstName : fqudqpfgq, lastName : ld, fullName : fqudqpfgq ld }

In this case, the **firstName** and **lastName** will be randomized thanks to the annotation.
At the end, the method **customExample( )** will be called so fullName will be the firstName and lastName that have been randomized.

</details>


<details>
    <summary>
        <i>Example using utility class in custom implementation</i>
    </summary>

In this example, we want to randomize fields but with specific behavior.

In this case utility class can be used for this purpose.

**First** we set **@Anonyme** annotation on the top of the class, then we create a method that will be annotated with **@SelfImplementation**.

````java
@Anonyme
public class Person {

    private String fullName = "";

    @SelfImplementation
    public void customExample() {
        fullName = StringGenerator.generateString() + " " + StringGenerator.generateString(4, 5);
    }

    /*  Getter and Setter necessary */
    /* Constructor */
}
````

````java
public class Main {
    public static void main(String[] args) {
        Person p = new Person();  // create a new Person
        ObjectAnonymizer oa = new ObjectAnonymizer();  // instance of objectAnonymizer 
        oa.anonymize(p);  // apply anonymization to p
    }
}
````

    Output:
    p -> { fullName : buczohco jfpz }

Here **fullName** will be constructed as a random string of random size between default value given
[here](#staticStringGenerator) and a random string of size between 4 and 5, separated by a space.

</details>

<details>
    <summary>
        <i>Example without annotation</i>
    </summary>

In this example, we want to randomize **firstName** and **lastName** and anonymize the cv without any annotation.

Here the old cv is deleted then create a new cv without any content.

````java
public class Person {

    private String firstName = "";

    private String lastName = "";

    private String cvUri = "path/to/file.pdf";

    /*  Getter and Setter */
    /* Constructor */
}
````

````java
import org.avisto.anonymization.generator.StringGenerator;

public class Main {
    public static void main(String[] args) {
        Person p = new Person("Zoo", "Landers", "tmp/cv/cv_zou_landers.pdf");  // create a new Person
        p.setFirstName(StringGenerator.generateString());
        p.setLastName(StringGenerator.generateString());
        FileGenerator.deleteFile(p.getCvUri());
        FileGenerator.generateFile("tmp/cv/", "new_name", "pdf");
    }
}
````

    Output: 
    p -> { firstName : bqcioud, lastName : chquif, cvUri : tmp/cv/new_name.pdf }

The file "tmp/cv/new_name.pdf" is created.

</details>

#### Utility class

<details>
    <summary>
       <i>StringGenerator</i>
    </summary>

````java
public class Main {
    public static void main(String[] args) {
        String v1 = StringGenerator.generateString();
        // v1 = a random string with length between default value define in StringGenerator class
        // output : v1 -> sdosgsgios

        String v2 = StringGenerator.generateString(1, 3);
        // v2 = a random string with length between 1 and 3
        // output : v2 -> fid

        String v3 = StringGenerator.generateNumber(10, 16, 3);
        // v3 = a number as string between the first and second parameter and fill with 0 at the beginning to have a length equals to the last parameter
        // output : v3 -> 014
        
        String v4 = StringGenerator.generateNumber(4);
        // v4 = string of 4 number
        // output : v4 -> 9463

        String v5 = StringGenerator.generateText(1, 5);
        // v5 = string of length between 1 and 5, the string is based on LOREM_IPSUM field in class StringGenerator
        // output : v5 -> Lor
            
        String v6 = StringGenerator.generateStringFromFile("tmp/file/example.txt");
        // v6 = random value get in the file given
        // output : v6 -> example

        String v7 = StringGenerator.generateFromRegex("[a-z]{2}[0-9]{3}");
        // v7 = string that match the pattern given
        // output : v7 -> rc083
    }
}
````

Here the file _example.txt_ contains :<br>

_________
An<br>
example
_________
    
</details>


<details>
    <summary>
       <i>NumberGenerator</i>
    </summary>

````java
public class Main {
    public static void main(String[] args) {
        int v1 = NumberGenerator.generateInt(); 
        // v1 = a random int

        long v2 = NumberGenerator.generateLong();
        // v2 = a random long

        float v3 = NumberGenerator.generateFloat();
        // v3 = a random float

        double v4 = NumberGenerator.generateDouble();
        // v4 = a random double

        int v5 = NumberGenerator.generateInt(10, 20);
        // v5 = a random int between 10 and 20 exclude

        long v6 = NumberGenerator.generateLong(10L, 20L);
        // v6 = a random long between 10 and 20 exclude

        float v7 = NumberGenerator.generateFloat(10f, 20f);
        // v7 = a random float between 10 and 20 exclude

        double v8 = NumberGenerator.generateDouble(10d, 20d);
        // v8 = a random double between 10 and 20 exclude
    }
}
````

</details>

<details>
    <summary>
       <i>FileGenerator</i>
    </summary>

````java
public class Main {
    public static void main(String[] args) {
        String v1 = FileGenerator.generateFile("/tmp", "name_of_file", "pdf"); // create a pdf file
        // v1 = string corresponding of path where the file has been created
        // output : v1 -> /tmp/name_of_file.pdf

        String v2 = FileGenerator.generateFile("/tmp/name_of_file.pdf"); // create a pdf file
        // v2 = string corresponding of path where the file has been created
        //  output : v2 -> /tmp/name_of_file.pdf

        String v3 = FileGenerator.generateFile("/tmp/my_base_directory/", "/tmp/name_of_file.png"); // search a base file name base.png in the directory given as first parameter then create a copy to the path given in second parameter
        // v3 = string corresponding of path where the file has been created
        //  output : v3 -> /tmp/name_of_file.png

        byte[] v4 = FileGenerator.generateFileAsBytes("jpeg");
        // v4 = array of byte corresponding of a base jpeg file
        // output : v4 -> [65, -115, 30, 78, -10, ...]

        byte[] v5 = FileGenerator.generateFileAsBytes("/tmp/my_base_directory/", "txt"); // search a base file name base.txt in the directory given as first parameter to return as byte array
        // v5 = array of byte corresponding of a base file txt get in the directory given as first param
        // output : v5 -> []

        String v6 = FileGenerator.getExtension("/tmp/name_of_file.pdf");
        // v6 = extension of the file
        // output : v6 -> pdf

        boolean v7 = FileGenerator.deleteFile("/tmp/file_to_delete.docx");
        // v7 = boolean that indicate if it has deleted the file given properly
        // output : v5 -> true
    }
}
````
</details>

<details>
    <summary>
       <i>BooleanGenerator</i>
    </summary>

````java
import org.avisto.anonymization.generator.BooleanGenerator;

public class Main {
    public static void main(String[] args) {
        boolean v1 = BooleanGenerator.generateBoolean();
        // v1 = boolean that have 50% chance to be true 
        // output : v1 -> true

        boolean v2 = BooleanGenerator.generateBoolean(0.3);
        // v2 = boolean that have 30% chance to be true
        // output : v2 -> false
    }
}
````
</details>

</details>

<details>
    <summary>
       <i>Generator</i>
    </summary>

````java
import org.avisto.anonymization.generator.BooleanGenerator;
import org.avisto.anonymization.generator.Generator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> values = new ArrayList<>();
        values.add("first value");
        values.add("second value");
        
        String v1 = Generator.generateValueFromCollection(values);
        // v1 = random value from values 
        // output : v1 -> first value

        int v2 = Generator.generateValueFromCollection(new Integer[]{12, 10, 8});
        // v2 = random value from array given 
        // output : v2 -> 10
        // work only on arrays of Objects not primitives
    }
}
````
Note that generateValueFromCollection works on any type which is inside the collection given.

Here we used String and Integer, but it can be any Object.

</details>


# Technical details

## Enum

### NumberType
**Enum that represents all handled number types generation.**
<details>
    <summary>
        <i>Available values</i>
    </summary>

Values:
- `LONG`
- `INT`
- `FLOAT`
- `DOUBLE`
</details>

### StringType
**Enum that represents all handled string formats generation.**

<details>
    <summary>
        <i>Available values</i>
    </summary>

Values:
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
        <i>Parameters</i>
    </summary>

|            name | type       | is optional | default   | description                                |
|----------------:|------------|-------------|-----------|--------------------------------------------|
|           value | NumberType | false       | none      | behavior                                   |
|        minValue | String     | true        | "default" | min value                                  |
|        maxValue | String     | true        | "default" | max value                                  |
|         minSize | int        | true        | 1         | min size of the collection                 |
|         maxSize | int        | true        | 15        | max size of the collection                 |
|        isUnique | boolean    | true        | false     | specify if a field as a unique key         |
|   randomizeNull | boolean    | true        | false     | specify if null field should be anonymized |

The default min (alt. max) value is the minimal (alt. maximal) value possible depending on the NumberType.

The size of the collection is selected randomly between minSize and maxSize.

minSize and maxSize are used only if the field is a collection.

</details>

<details>
    <summary>
        <i>Parameters usage by NumberType value</i>
    </summary>

|   value | parameters                                                    | description     |
|--------:|---------------------------------------------------------------|-----------------|
|    LONG | minValue, maxValue, minSize, maxSize, isUnique, randomizeNull | generate long   |
|     INT | minValue, maxValue, minSize, maxSize, isUnique, randomizeNull | generate int    |
|   FLOAT | minValue, maxValue, minSize, maxSize, isUnique, randomizeNull | generate float  |
|  DOUBLE | minValue, maxValue, minSize, maxSize, isUnique, randomizeNull | generate double |

</details>

### RandomizeString <a id='stringAnnotation'></a>

<details>
    <summary>
        <i>Parameters</i>
    </summary>

|           name | type            | is optional | default         | description                                |
|---------------:|-----------------|-------------|-----------------|--------------------------------------------|
|          value | StringType      | false       | none            | behavior                                   |
|      minLength | int             | true        | "default"       | min length                                 |
|      maxLength | int             | true        | "default"       | max length                                 |
|           path | String          | true        | ""              | path of the file where to get values       |
| possibleValues | Array\<String\> | true        | {}              | array of different values that can be set  |
|        minSize | int             | true        | 1               | min size of the collection                 |
|        maxSize | int             | true        | 10              | max size of the collection                 |
|        pattern | String          | true        | "\[a-z\]{5,12}" | regex pattern                              |
|       isUnique | boolean         | true        | false           | specify if a field as a unique key         |
|  randomizeNull | boolean         | true        | false           | specify if null field should be anonymized |

The default minLength (alt. maxLength) value is the minimal (alt. maximal) length possible depending on the StringType,
the final length is selected randomly between minLength and MaxLength.

The size of the collection is selected randomly between minSize and maxSize.

minSize and maxSize are used only if the field is a collection.

See supported regex pattern syntax [here](https://github.com/curious-odd-man/RgxGen#supported-syntax).

</details>

<details>
    <summary>
        <i>Parameters usage by StringType value</i>
    </summary>

|                  value | parameters                                                      | description                                                                               |
|-----------------------:|-----------------------------------------------------------------|-------------------------------------------------------------------------------------------|
|                 STRING | minLength, maxLength, minSize, maxSize, isUnique, randomizeNull | generate random string, the alphabet is \[a-z\]                                           |
|                   TEXT | minLength, maxLength, minSize, maxSize, isUnique, randomizeNull | generate "Lorem ipsum" text                                                               |
|                  EMAIL | minSize, maxSize, isUnique, randomizeNull                       | generate random email with format : %s.%s@%s.%s \*                                        |
|                    URL | minSize, maxSize, isUnique, randomizeNull                       | generate random url with format : \[https, http\]://%s/%s/%s \*                           |
|    PHONE_INTERNATIONAL | minSize, maxSize, isUnique, randomizeNull                       | generate international phone number                                                       |
|               PHONE_FR | minSize, maxSize, isUnique, randomizeNull                       | generate french national phone number                                                     |
| SOCIAL_SECURITY_NUMBER | minSize, maxSize, isUnique, randomizeNull                       | generate random social security number with format : \[0,1\]\[0-9\]{2}\[01-12\]\[0-9\]{8} |
|          LICENSE_PLATE | minSize, maxSize, isUnique, randomizeNull                       | generate license plate with format \[A-Z\]{2}-\[0-9\]{3}-\[A-Z\]{2}                       |
|       STRING_FROM_FILE | path, minSize, maxSize, isUnique, randomizeNull                 | select value from file                                                                    |
|      STRING_FROM_ARRAY | possibleValues , minSize, maxSize, isUnique, randomizeNull      | select value from array                                                                   |
|                 NUMBER | minLength, maxLength, minSize, maxSize, isUnique, randomizeNull | generate number as string                                                                 |
|                  REGEX | pattern, minSize, maxSize, isUnique, randomizeNull              | generate string which respects the pattern                                                |
|                   IPV4 | minSize, maxSize, isUnique, randomizeNull                       | generate string which respects IPV4 format                                                |
|                   IPV6 | minSize, maxSize, isUnique, randomizeNull                       | generate string which respects IPV6 format                                                |

 `*` Replace %s by a random string.

</details>


### RandomizeFile

<details>
    <summary>
        <i>Parameters</i>
    </summary>

|             name | type             | is optional | default                                                                     | description                                      |
|-----------------:|------------------|-------------|-----------------------------------------------------------------------------|--------------------------------------------------|
|  pathToDirectory | String           | false       | none                                                                        | directory where to save new file                 |
| nameFileBehavior | @RandomizeString | true        | @RandomizeString(value = StringType.STRING, minLength = 15, maxLength = 30) | behavior of how to generate the name of new file |
|        removeOld | boolean          | true        | true                                                                        | define if the old file should be removed         |
|          minSize | int              | true        | 1                                                                           | min size of the collection                       |
|          maxSize | int              | true        | 15                                                                          | max size of the collection                       |

The size of the collection is selected randomly between minSize and maxSize.

See more about nameFileBehavior on [RandomizeString](#stringAnnotation).

</details>

<details>
    <summary>
        <i>Handled formats</i>
    </summary>

|    format    | extension                                   |
|:------------:|---------------------------------------------|
|    image     | `bmp` `gif` `png` `jpg` `jpeg` `tiff` `svg` |
|     text     | `odt` `docx` `txt`                          |
| presentation | `ods` `pptx`                                |
| spreadsheet  | `odp` `xlsx` `csv`                          |
|    video     | `mp4`                                       |
|    audio     | `mp3` `m4a` `flac` `ogg` `wav`              |
|    other     | `pdf` `html` `htm`                          |


</details>

### RandomizeEnum

<details>
    <summary>
        <i>Parameters</i>
    </summary>

|          name | type    | is optional | default | description                                  |
|--------------:|---------|-------------|---------|----------------------------------------------|
| randomizeNull | boolean | true        | false   | specify if a null field should be anonymized |

</details>

Set a value randomly get from the Enum.

Doesn't work on collection of Enum yet.

### SelfImplementation

All methods that are annotated with @SelfImplementation are called after randomizing attribute. Methods must have no parameter and be public.

This can be used if you want to make custom behavior on some field.


## Generator

All methods used in the library, except for regex, use these classes to generate different values. These classes are utility classes, that means it doesn't need to be instantiated.

|    class name    |
|:----------------:|
| StringGenerator  |
| NumberGenerator  |
| BooleanGenerator |
|  FileGenerator   |
|    Generator     |

See more about these classes and methods below.

</details>

<details>
    <summary>
        <i>Generator</i>
    </summary>

### static method

`generateValueFromCollection( List<T> origin )` => Return a random value selected from the given list.

`generateValueFromCollection( T[] origin )` => Return a random value selected from the given array.

</details>

<details>
    <summary>
        <i>BooleanGenerator</i>
    </summary>

### static method

`GenerateBoolean( )` => Return a random boolean with a probability equals to 50% for true and 50% for false.

`GenerateBoolean( Float probability )` => Return a random boolean with a probability equals to { probability } for true and { 1 - probability } for false.

</details>


<details>
    <summary>
        <i>NumberGenerator</i>
    </summary>

### static method

`generateDouble( )` => Return a random Double.

`generateDouble( Double min, Double max )` => Generate a random Double, the value is between [ min, max [.

`generateFloat( )` => Return a random Float.

`generateFloat( Float min, Float max )` => Generate a random Float, the value is between [ min, max [.

`generateInt( )` => Return a random Integer.

`generateInt( Int min, Int max )` => Generate a random Int, the value is between [ min, max [.

`generateLong( )` => Return a random Long.

`generateLong( Long min, Long max )` => Generate a random Long, the value is between [ min, max [.

</details>

<details>
    <summary>
        <i>StringGenerator</i>
    </summary>

### static field <a id='staticStringGenerator'></a>
int **DEFAULT_MAX_LENGTH** = 12

int **DEFAULT_MAX_SIZE** = 10

int **DEFAULT_MIN_LENGTH** = 3

int **DEFAULT_MIN_SIZE** = 1

String **LOREM_IPSUM** => basic lorem ipsum

### static method

`generateNumber( int length )` => Return a string of { length } digits.

`generateNumber( long minValue, long maxValue, int digits )` => Return a string of random numbers in a minValue and maxValue.
If the value doesn't fill all digits it will complete it with enough 0 at the beginning.

`generateString( )` => Generate a random string with random length between [ DEFAULT_MIN_LENGTH, DEFAULT_MAX_LENGTH ].

`generateString( int minLength, int maxLength )` => Generate a random string with random length between [ minLength , maxLength ].
To generate a specific length, choose a value where minLength = maxLength.


`generateStringFromFile( String path )` => Select a random value from a file, the value in file must match this format:
_________
data1<br>
data2<br>
data3
_________
WARNING : It saves the content of the file on a map.
On consecutive calls, the file will not be re-opened each time, so if the path didn't change and the content of the file changed, it will not handle changes.


`generateText(int minLength, int maxLength)` => Generate "Lorem Ipsum" String with random length between [ minLength , maxLength ].
To generate a specific length, choose a value where minLength = maxLength.

`generateFromRegex( String pattern )` => Generate a string that match the pattern given
</details>

<details>
    <summary>
        <i>FileGenerator</i>
    </summary>

### static method

`deleteFile( String pathToFile )` =>
Delete the file given in param.

`generateFile( String pathToFile )` =>
Generate and save a file on the path given, based on blank file put in resources in this library.<br>
It will get the extension expected on the pathToFile param.

`generateFile( String originDirectory, String pathToFile )` =>
Generate a file based on file in originDirectory. To work, files must match name: base.[extension]<br>
It will get the extension expected on the pathToFile param.

`generateFile( String pathToDirectory, String name, String extension )` => 
Generate a file to the pathToDirectory with the name and extension given, based on blank file in resources in this library depending on the extension given.

`generateFileAsInputStream( String extension )` =>
It will return the InputStream corresponding to the file depending on the extension given.
It will get a template file on resources from this library.

`generateFileAsBytes( String extension )` =>
It will return the byte array corresponding to the file depending on the extension given. 
It will get a template file on resources from this library.

`generateFileAsBytes( String originDirectory, String extension )` =>
It will return the byte array corresponding to the file depending on the extension given.
It will get a template file on the originDirectory given, the template must match name base.[extension] .

`getExtension( String pathToFile )` => Get and return the extension of a file given.

</details>

You can also generate value by creating a class with annotated field with RandomizeNull parameter.

<details>
    <summary>
        <i>Example of generation from an uninitialized class</i>
    </summary>

````java
@Anonyme
public class Person {

    @RandomizeString(value = StringType.STRING, randomizeNull = true)
    private String firstName;

    @RandomizeString(value = StringType.STRING, randomizeNull = true)
    private String lastName;

    @RandomizeString(value = StringType.EMAIL, randomizeNull = true)
    private String email;

    @RandomizeString(value = StringType.NUMBER, randomizeNull = true)
    private String number;
    /*  Getter and Setter necessary */
    /* Constructor */
}
````

````java
public class Main {
    public static void main(String[] args) {
        Person p = new Person();  // create a new Person
        ObjectAnonymizer oa = new ObjectAnonymizer();  // instance of objectAnonymizer 
        oa.anonymize(p);  // apply anonymization to p
    }
}
````

    Output:
    Before ObjectAnonymizer:
    p -> { firstName : null, lastName : null, email : null, number : null}
    After ObjectAnonymizer:
    p -> { firstName : adispmd, lastName : zqsahr, email : azrfsq.gfzeryda@gyfdg.ftd, number : 791310314}

</details>

## Limitations

**Table of all handled types**

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

The regex generation is based on library [RgxGen version 1.4](https://github.com/curious-odd-man/RgxGen/tree/1.4).

Getter and Setter must be declared with a name format: setFieldName, getFieldName. 

This library works with default getter and setter built with Lombok.

If a field has null value it will stay null.

The library works on Java 11+.

## Contributing
/!\ TO COMPLETE

## License
/!\ TO COMPLETE 

