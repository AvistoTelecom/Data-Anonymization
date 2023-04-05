/*
 *
 */

import model.Model;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
import org.avisto.anonymization.exception.FileException;
import org.avisto.anonymization.exception.HandleExtensionException;
import org.avisto.anonymization.exception.PathException;
import org.avisto.anonymization.generator.FileGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileAnonymizationTest {

    private final String directory = "src/test/resources/file/new/";

    @BeforeEach
    @AfterEach
    public void cleanUpFiles() {
        File directory = new File("src/test/resources/file/new/");
        for(File file: Objects.requireNonNull(directory.listFiles()))
            if (!file.isDirectory())
                file.delete();
    }

    @Test
    public void testGetExtension() {
        Assertions.assertEquals("txt", FileGenerator.getExtension("file.txt"));
        Assertions.assertEquals("", FileGenerator.getExtension("file"));

    }



    @Test
    public void testDeleteFile() throws IOException {
        String fileName = "testFile.txt";
        Path newFilePath = Paths.get(directory + fileName);
        Files.createFile(newFilePath);

        Assertions.assertTrue(FileGenerator.deleteFile(directory + fileName));
        Assertions.assertFalse(FileGenerator.deleteFile(directory + fileName));
    }

    @Test
    public void testGenerateFile() throws FileException {
        String newFile = FileGenerator.generateFile(directory, "file", "txt");
        Assertions.assertEquals("src/test/resources/file/new/file.txt", newFile);
        File file = new File("src/test/resources/file/new/file.txt");
        Assertions.assertTrue(file.exists());

        FileException e = Assertions.assertThrows(HandleExtensionException.class, () -> FileGenerator.generateFile(directory, "file", "notHandle"));
        Assertions.assertEquals("extension not handled", e.getMessage());

        e = Assertions.assertThrows(PathException.class, () -> FileGenerator.generateFile("no/directory/there", "file", "txt"));
        Assertions.assertEquals("unknown path", e.getMessage());
    }

    @Test
    public void testAnnotation() throws IOException {
        Path newFilePath = Paths.get("src/test/resources/file/new/file.txt");
        Files.createFile(newFilePath);
        Model.ModelWithFile model = new Model.ModelWithFile();
        ObjectAnonymizer anonymizer = new ObjectAnonymizer();
        anonymizer.anonymize(model);

        Assertions.assertEquals("src/test/resources/file/new/newFile.txt", model.getValue());

        File file = new File("src/test/resources/file/new/newFile.txt");
        Assertions.assertTrue(file.exists());

        file = new File("src/test/resources/file/new/file.txt");
        Assertions.assertFalse(file.exists());
    }

    @Test
    public void testAnnotationWithFalseRemoval() throws IOException {
        Path newFilePath = Paths.get("src/test/resources/file/new/file.txt");
        Files.createFile(newFilePath);
        Model.ModelWithOldFileRemove model = new Model.ModelWithOldFileRemove();
        ObjectAnonymizer anonymizer = new ObjectAnonymizer();
        anonymizer.anonymize(model);

        Assertions.assertEquals("src/test/resources/file/new/newFile.txt", model.getValue());

        File file = new File("src/test/resources/file/new/file.txt");
        Assertions.assertTrue(file.exists());

        file = new File("src/test/resources/file/new/newFile.txt");
        Assertions.assertTrue(file.exists());
    }

    @Test
    public void testConvert() {
        FileGenerator.generateFile("src/test/resources/file/new/file.htm");
        File file = new File("src/test/resources/file/new/file.htm");
        Assertions.assertTrue(file.exists());

        FileGenerator.generateFile("src/test/resources/file/new/file.jpeg");
        file = new File("src/test/resources/file/new/file.jpeg");
        Assertions.assertTrue(file.exists());
    }

    @Test
    public void testGenerateFileFromOrigin() {
        FileGenerator.generateFile("src/test/resources/file/base/", "src/test/resources/file/new/file.txt");
        File file = new File("src/test/resources/file/new/file.txt");
        Assertions.assertTrue(file.exists());

        PathException e = Assertions.assertThrows(PathException.class, () -> FileGenerator.generateFile("wrong/path/", "src/test/resources/file/new/file.txt"));
        Assertions.assertEquals("unknown path", e.getMessage());

        e = Assertions.assertThrows(PathException.class, () -> FileGenerator.generateFile("src/test/resources/file/base/", "wrong/path/file.txt"));
        Assertions.assertEquals("unknown path", e.getMessage());
    }
}
