package org.avisto.anonymization.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Class that generate String with random basis on a based format
 * @author desaintpern
 */
public final class StringGenerator {

    private static final String ALPHABET = "aeiouyzrtpqsdfghjklmwxcvbn";
    public static final int DEFAULT_MIN_LENGTH = 3;
    public static final int DEFAULT_MAX_LENGTH = 12;
    public static final int DEFAULT_MIN_SIZE = 1;
    public static final int DEFAULT_MAX_SIZE = 10;

    public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit. Ut velit mauris, egestas sed, gravida nec, ornare ut, mi. Aenean ut orci vel massa suscipit pulvinar. Nulla sollicitudin. Fusce varius, ligula non tempus aliquam, nunc turpis ullamcorper nibh, in tempus sapien eros vitae ligula. Pellentesque rhoncus nunc et augue. Integer id felis. Curabitur aliquet pellentesque diam. Integer quis metus vitae elit lobortis egestas. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi vel erat non mauris convallis vehicula. Nulla et sapien. Integer tortor tellus, aliquam faucibus, convallis id, congue eu, quam. Mauris ullamcorper felis vitae erat. Proin feugiat, augue non elementum posuere, metus purus iaculis lectus, et tristique ligula justo vitae magna. Aliquam convallis sollicitudin purus. Praesent aliquam, enim at fermentum mollis, ligula massa adipiscing nisl, ac euismod nibh nisl eu lectus. Fusce vulputate sem at sapien. Vivamus leo. Aliquam euismod libero eu enim. Nulla nec felis sed leo placerat imperdiet. Aenean suscipit nulla in justo. Suspendisse cursus rutrum augue. Nulla tincidunt tincidunt mi. Curabitur iaculis, lorem vel rhoncus faucibus, felis magna fermentum augue, et ultricies lacus lorem varius purus. Curabitur eu amet.";


    private static final Map<String, List<String>> FILE_VALUE= new HashMap<>();


    /**
     * generate a random String with random length between [ minLength , maxLength ] <br/>
     * to generate a specific length, choose a value where minLength = maxLength
     * @param minLength min length of the string to generate
     * @param maxLength max length of the string to generate
     * @return random string with a size between [ minLength, maxLength ]
     */
    public static String generateString(int minLength, int maxLength) {
        int size = NumberGenerator.generateInt(minLength, maxLength + 1);
        String result = "";
        int valueToPick;

        for (int i = 0; i < size; i++) {
            valueToPick = NumberGenerator.generateInt(0, ALPHABET.length());
            result  = result.concat(String.valueOf(ALPHABET.charAt(valueToPick)));
        }
        return result;
    }

    /**
     * generate a string of number randomly in a minValue and maxValue <br/>
     * if the value doesn't fill all digits it will complete it with much 0 at the beginning
     * @param minValue min value of the string
     * @param maxValue max value of the string
     * @param digits length of the string
     * @return return a string of number of size digits
     */
    public static String generateNumber(int minValue, int maxValue, int digits) {
        if (((int) Math.log10(maxValue) + 1) > digits || digits == 0) throw new IllegalArgumentException("not enough number of digits to represent max value");
        String value = NumberGenerator.generateInt(minValue, maxValue + 1).toString();
        String result = "";
        for (int i = value.length(); i < digits; i++) {
            result = result.concat("0");
        }
        return result.concat(value);
    }

    /**
     * select a random value from a file,
     * the file must match this format: <br/>
     * ====================== <br/>
     * data1 <br/>
     * data2 <br/>
     * data3 <br/>
     * . <br/>
     * . <br/>
     * . <br/>
     * ====================== <br/>
     * @param path path to access the file where data are
     * @return the selected random value
     */
    public static String generateStringFromFile(String path) {
        addToFileValueIfNotExist(path);
        return generateStringFromCollection(FILE_VALUE.get(path));
    }

    /**
     * select a Stream of random values from a file <br/>
     * ====================== <br/>
     * data1 <br/>
     * data2 <br/>
     * data3 <br/>
     * . <br/>
     * . <br/>
     * . <br/>
     * ====================== <br/>
     * @param size the size of the Stream to return
     * @param path path to access the file where data are
     * @return Stream of {size} randomly selected value in file
     */
    public static Stream<String> generateStringsFromFile(int size, String path) {
        addToFileValueIfNotExist(path);
        return generateStringsFromCollection(size, FILE_VALUE.get(path));
    }

    /**
     * select a random value in the parameter origin
     * @param origin List of possible value
     * @return the random value selected in origin
     */
    public static String generateStringFromCollection(List<String> origin) {
        return origin.get(NumberGenerator.generateInt(0, origin.size()));
    }

    /**
     * select randomly in the parameter origin, {size} value in origin to generate the Stream, <br/>
     * there can be the same value selected multiple time
     * @param size the size of the Stream to return
     * @param origin Stream of possible value
     * @return Stream of {size} randomly selected value in origin
     */
    public static Stream<String> generateStringsFromCollection(int size, List<String> origin) {
        return generateStringStream(size, () ->  generateStringFromCollection(origin) );
    }

    /**
     * generate a Stream of string, <br/>
     * the way to generate string is define in the supplier
     * @param size size of the Stream to return
     * @param supplier behavior of string generation
     * @return Stream of string generate with the supplier
     */
    public static Stream<String> generateStringStream(int size, Supplier<String> supplier) {
        String[] result = new String[size];
        for (int i = 0; i < result.length; i++) {
            result[i] = supplier.get();
        }
        return Arrays.stream(result);
    }

    /**
     * add to the map the value of the file if it doesn't exist yet
     * @param path path to access the file where data are
     */
    private static void addToFileValueIfNotExist(String path) {
        if (!FILE_VALUE.containsKey(path)) {
            FILE_VALUE.put(path, getLinesFromFile(path));
        }
    }

    /**
     * put on a Stream, line by line, all lines from the file
     * @param  path to access the file where data are
     * @return Stream of lines
     */
    private static List<String> getLinesFromFile(String path){
        List<String> result = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(result::add);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("file %s not found", path), e);
        }
        return result;
    }

    /**
     * this class can't be instantiated
     */
    private StringGenerator() {
    }
}
