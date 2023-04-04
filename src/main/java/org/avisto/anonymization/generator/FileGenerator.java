/*
 *
 */

package org.avisto.anonymization.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class FileGenerator {

    private static final String SOURCE_DIRECTORY = "src/main/resources/file/base/";

    /**
     * Generate a file based on blank file in resources in this library.
     * To see all extensions handled see the README.md
     * @param pathToDirectory the directory where to save the new file
     * @param name name of the new file
     * @param extension the extension type to produce
     * @return path to the new file created, if there are an exception then null
     */
    public static String generateFile(String pathToDirectory, String name, String extension) {
        String newFile = pathToDirectory + name + "." + extension;
        return generateFile(newFile);
    }

    /**
     * Generate a file based on blank file in resources in this library.
     * To see all extensions handled see the README.md
     * @param pathToFile the path of the file
     * @return path to the new file created, if there are an exception then null
     */
    public static String generateFile(String pathToFile) {
        Path original = Paths.get(String.format("%sbase.%s", SOURCE_DIRECTORY, getExtension(pathToFile)));
        Path copy = Paths.get(pathToFile);
        try {
            Files.copy(original, copy, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            return null;
        }
        return pathToFile;
    }

    /**
     * get and return the extension of a file given
     * @param pathToFile file to get extension
     * @return extension
     */
    public static String getExtension(String pathToFile) {
        String extension = "";

        int i = pathToFile.lastIndexOf('.');
        if (i > 0) {
            extension = pathToFile.substring(i+1);
        }
        return extension;
    }

    /**
     * delete the file given in param.
     * @param pathToFile path to the file to delete
     * @return true if file have been deleted else false
     */
    public static boolean deleteFile(String pathToFile) {
        Path fileToDeletePath = Paths.get(pathToFile);
        try {
            return Files.deleteIfExists(fileToDeletePath);
        }
        catch (IOException e) {
            return false;
        }
    }

    /**
     * this class can't be instantiated
     */
    private FileGenerator() {}
}
