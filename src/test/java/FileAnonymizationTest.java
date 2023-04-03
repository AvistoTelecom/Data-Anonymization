/*
 *
 */

import model.Model;
import org.avisto.anonymization.anonymizer.ObjectAnonymizer;
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
    private final String fileName = "testFile.txt";

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
        Path newFilePath = Paths.get(directory + fileName);
        Files.createFile(newFilePath);

        Assertions.assertTrue(FileGenerator.deleteFile(directory + fileName));
        Assertions.assertFalse(FileGenerator.deleteFile(directory + fileName));
    }

    @Test
    public void testGenerateFile() {
        String newFile = FileGenerator.generateFile(directory, "file", "txt");
        Assertions.assertEquals("src/test/resources/file/new/file.txt", newFile);
        File file = new File("src/test/resources/file/new/file.txt");
        Assertions.assertTrue(file.exists());

        String newFileNull = FileGenerator.generateFile(directory, "file", "notHandle");
        Assertions.assertNull(newFileNull);
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
}
