/*
 *
 */

package org.avisto.anonymization.generator;

import org.avisto.anonymization.exception.HandleExtensionException;
import org.avisto.anonymization.exception.PathException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class FileGenerator implements Generator{


    /**
     * Generate a file based on blank file in resources in this library.
     * To see all extensions handled see the README.md
     * @param pathToDirectory the directory where to save the new file
     * @param name name of the new file
     * @param extension the extension type to produce
     * @return path to the new file created, if there are an exception then null
     * @throws HandleExtensionException throws when the extension is not handled yet
     * @throws PathException throws when the path to save file is wrong
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
     * @throws HandleExtensionException throws when the extension is not handled yet
     * @throws PathException throws when the path to save file is wrong
     */
    public static String generateFile(String pathToFile) {
        InputStream val = FileGenerator.class.getClassLoader().getResourceAsStream("file/base/base." + convert(getExtension(pathToFile)));
        if (val == null) throw new HandleExtensionException("extension not handled");
        try {
            Files.copy(val, Paths.get(pathToFile), StandardCopyOption.REPLACE_EXISTING);
            return pathToFile;
        }
        catch (IOException e) {
            throw new PathException("unknown path", e);
        }
    }

    /**
     * Generate a file based on file in originDirectory.<br>
     * To work, files must match base.[extension]
     * @param pathToFile the path of the file
     * @param originDirectory path to directory where to find base file (ex: "/dir/of/files/")
     * @return path to the new file created, if there are an exception then null
     * @throws HandleExtensionException throws when the extension is not handled yet
     * @throws PathException throws when the path to save file is wrong
     */
    public static String generateFile(String originDirectory, String pathToFile) {
        try {
            Files.copy(Paths.get(originDirectory + "base." + getExtension(pathToFile)), Paths.get(pathToFile), StandardCopyOption.REPLACE_EXISTING);
            return pathToFile;
        }
        catch (IOException e) {
            throw new PathException("unknown path", e);
        }
    }

    /**
     * get and return the extension in lower case of a file given
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
     * @param extension extension to convert
     * @return convertion
     */
    private static String convert(String extension) {
        extension = extension.toLowerCase();
        switch (extension) {
            case "htm":
                return "html";
            case "jpeg":
                return "jpg";
            default:
                return extension;
        }
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
