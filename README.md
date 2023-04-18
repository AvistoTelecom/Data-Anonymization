# Anonymization

## Description

This library have for main purpose to randomize sensitive data.
For this purpose, this library gives numerous tools to achieve anonymization.

The first one is by using annotation, the second one is by using utility class available.

## Installation

/!\ à compléter 

## Usage
To allow the ObjectAnonymizer to anonymize your Object, the Class must be annotated with @Anonyme.

Then all fields that need to be anonymized must be annotated with annotation depending on the field type.

If a field is null, then it will let the value to null.

Following, some examples of how to use the library.

### Examples

#### Basic use
<details>
    <summary>
        Simple Example using only annotation
    </summary>

In this example, we want to randomize value with the simplest way.
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

There, all fields annotated will be randomized based on the behavior given in the annotation.

</details>

<details>
    <summary>
        Example using custom implementation
    </summary>

In this example, we want to randomize firstName and lastName using annotation, we want fullName as firstName and lastName anonymized separated by a space.

For this, we can use **@SelfImplementation** as following to consider other fields during the anonymization.

First, we set annotation **@Anonyme** on the class, then we annotate fields firstName and lastName.
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

In this case, the firstName and lastName will be randomized by annotation.
At the end, the method customExample( ) will be called so fullName will be the firstName and lastName that have been randomized.

</details>


<details>
    <summary>
        Example using utility class in custom implementation
    </summary>

In this example, we want to randomize fields but with specific behavior.

In this case utility class can be used for this purpose.

First we set annotation **@Anonyme** on the class, then we create a method that will be annotated with **@SelfImplementation**.

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

Here full name will be constructed as a random string of random size between default value given
[here](#staticStringGenerator) and a random string of size between 4 and 5, separated by a space.

</details>

<details>
    <summary>
        Example without annotation
    </summary>

In this example, we want to randomize firstName and lastName and anonymize the CV without any annotation.

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

</details>

#### utility class

<details>
    <summary>
       StringGenerator
    </summary>

````java
import org.avisto.anonymization.generator.StringGenerator;

public class Main {
    public static void main(String[] args) {
        String v1 = StringGenerator.generateString();
        // v1 = a random string with length between default value define in StringGenerator class

        String v2 = StringGenerator.generateString(1, 3);
        // v2 = a random string with length between 1 and 3

        String v3 = StringGenerator.generateNumber(10, 16, 3);
        // v3 = a number as string between the first and second parameter and fill with 0 at the beginning to have a length equals to the last parameter

        String v4 = StringGenerator.generateNumber(4);
        // v4 = string of 4 number

        String v5 = StringGenerator.generateText(1, 5);
        // v5 = string of length between 1 and 5, the string is based on LOREM_IPSUM field in class StringGenerator

        String v6 = StringGenerator.generateStringFromFile("path/To/File/example.txt");
        // v6 = random value get in the file given

        String v7 = StringGenerator.generateFromRegex("[a-z]{2}[0-9]{3}");
        // v7 = string that match the pattern given 
    }
}
````
Here the file example.txt contains :<br>
_________
An<br>
example
_________

See supported regex pattern syntax [here](https://github.com/curious-odd-man/RgxGen#supported-syntax).

</details>


<details>
    <summary>
       NumberGenerator
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
       FileGenerator
    </summary>

````java
public class Main {
    public static void main(String[] args) {
        String v1 = FileGenerator.generateFile("/tmp", "name_of_file", "pdf"); // create a file of extension pdf
        // v1 = string corresponding of path where the file has been created
        // here : /tmp/name_of_file.pdf

        String v2 = FileGenerator.generateFile("/tmp/name_of_file.pdf"); // create a file of extension pdf
        // v2 = string corresponding of path where the file has been created
        // here : /tmp/name_of_file.pdf

        String v3 = FileGenerator.generateFile("/tmp/my_base_directory/", "/tmp/name_of_file.png"); // search a base file name base.png in the directory given as first parameter then create a copy to the path given in second parameter
        // v3 = string corresponding of path where the file has been created
        // here : /tmp/name_of_file.png

        byte[] v4 = FileGenerator.generateFileAsBytes("jpeg");
        // v4 = array of byte corresponding of a base file of extension jpeg

        byte[] v5 = FileGenerator.generateFileAsBytes("/tmp/my_base_directory/", "txt"); // search a base file name base.txt in the directory given as first parameter to return as byte array
        // v5 = array of byte corresponding of a base file txt get in the directory given as first param

        String v6 = FileGenerator.getExtension("/tmp/name_of_file.pdf");
        // v6 = extension of the file
        // here : pdf

        boolean v7 = FileGenerator.deleteFile("/tmp/file_to_delete.docx");
        // v7 = boolean that indicate if it has deleted the file given properly
    }
}
````
</details>

<details>
    <summary>
       BooleanGenerator
    </summary>

````java
import org.avisto.anonymization.generator.BooleanGenerator;

public class Main {
    public static void main(String[] args) {
        boolean v1 = BooleanGenerator.generateBoolean();
        // v1 = boolean that have 50% chance to be true 

        boolean v2 = BooleanGenerator.generateBoolean(0.3);
        // v2 = boolean that have 30% chance to be true
    }
}
````
</details>

</details>

<details>
    <summary>
       Generator
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

        int v2 = Generator.generateValueFromCollection(new Integer[]{12, 10, 8});
        // v2 = random value from array given 
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
        Available values
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
        Available values
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
        Parameters
    </summary>

|     name | type       | is optional | default   | description                |
|---------:|------------|-------------|-----------|----------------------------|
|    value | NumberType | false       | none      | behavior                   |
| minValue | String     | true        | "default" | min value                  |
| maxValue | String     | true        | "default" | max value                  |
|  minSize | int        | true        | 1         | min size of the collection |
|  maxSize | int        | true        | 15        | max size of the collection |

The default min (alt. max) value is the minimal (alt. maximal) value possible depending on the NumberType.

The size of the collection is selected randomly between minSize and maxSize.

minSize and maxSize are used only if the field is a collection.

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

minSize and maxSize are used only if the field is a collection.

See supported regex pattern syntax [here](https://github.com/curious-odd-man/RgxGen#supported-syntax).

</details>

<details>
    <summary>
        Parameters usage by StringType value
    </summary>

|                  value | parameters                             | description                                                                               |
|-----------------------:|----------------------------------------|-------------------------------------------------------------------------------------------|
|                 STRING | minLength, maxLength, minSize, maxSize | generate random string, the alphabet is \[a-z\]                                           |
|                   TEXT | minLength, maxLength, minSize, maxSize | generate "Lorem ipsum" text                                                               |
|                  EMAIL | minSize, maxSize                       | generate random email with format : %s.%s@%s.%s \*                                        |
|                    URL | minSize, maxSize                       | generate random url with format : \[https, http\]://%s/%s/%s \*                           |
|    PHONE_INTERNATIONAL | minSize, maxSize                       | generate international phone number                                                       |
|               PHONE_FR | minSize, maxSize                       | generate french national phone number                                                     |
| SOCIAL_SECURITY_NUMBER | minSize, maxSize                       | generate random social security number with format : \[0,1\]\[0-9\]{2}\[01-12\]\[0-9\]{8} |
|          LICENSE_PLATE | minSize, maxSize                       | generate license plate with format \[A-Z\]{2}-\[0-9\]{3}-\[A-Z\]{2}                       |
|       STRING_FROM_FILE | path, minSize, maxSize                 | select value from file                                                                    |
|      STRING_FROM_ARRAY | possibleValues , minSize, maxSize      | select value from array                                                                   |
|                 NUMBER | minLength, maxLength, minSize, maxSize | generate number as string                                                                 |
|                  REGEX | pattern, minSize, maxSize              | generate string which respects the pattern                                                |
|                   IPV4 | minSize, maxSize                       | generate string which respects IPV4 format                                                |
|                   IPV6 | minSize, maxSize                       | generate string which respects IPV6 format                                                |

 `*` Replace %s by a random string.

</details>


### RandomizeFile

<details>
    <summary>
        Parameters
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
        Handled formats
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

Set a value randomly get from the Enum.

Doesn't work on collection of Enum yet.

### SelfImplementation

All methods that are annotated with @SelfImplementation are called after randomizing attribute. Methods must have no parameter and be public.

This can be used if you want to make custom behavior on some field.


## Generator

All methods used in the library, except for regex, use these classes to generate different values. These classes are utility class, that means it doesn't need to be instantiated.

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
        Generator
    </summary>

### static method

`generateValueFromCollection( List<T> origin )` => Return a random value in the parameter origin.

`generateValueFromCollection( T[] origin )` => Return a random value in the parameter origin.

</details>

<details>
    <summary>
        BooleanGenerator
    </summary>

### static method

`GenerateBoolean( )` => Return a random boolean with a probability equals to 50% for true and 50% for false.

`GenerateBoolean( Float probability )` => Return a random boolean with a probability equals to { probability } for true and { 1 - probability } for false.

</details>


<details>
    <summary>
        NumberGenerator
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
        StringGenerator
    </summary>

### static field <a id='staticStringGenerator'/>
int **DEFAULT_MAX_LENGTH** = 12

int **DEFAULT_MAX_SIZE** = 10

int **DEFAULT_MIN_LENGTH** = 3

int **DEFAULT_MIN_SIZE** = 1

String **LOREM_IPSUM** => basic lorem ipsum

### static method

`generateNumber( int length )` => Return a string of { length } number.

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
        FileGenerator
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

`generateFileAsBytes( String extension )` =>
It will return the byte array corresponding to the file depending on the extension given. 
It will get a template file on resources from this library.

`generateFileAsBytes( String originDirectory, String extension )` =>
It will return the byte array corresponding to the file depending on the extension given.
It will get a template file on the originDirectory given, the template must match name base.[extension] .

`getExtension( String pathToFile )` => Get and return the extension of a file given.

</details>

## Limitation

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

This library works with default getter and setter build with Lombok.

If a field has null value it will stay null.

## Contributing
/!\ TO COMPLETE

## License
/!\ TO COMPLETE 

