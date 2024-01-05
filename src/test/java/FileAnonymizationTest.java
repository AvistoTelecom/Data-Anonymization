/*
 *
 */

import java.io.InputStream;
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

    private static final String DIRECTORY = "src/test/resources/file/new/";
    private static final String FILE = "file.txt";
    private static final String NEWFILE = "newFile.txt";

    @BeforeEach
    public void createNewDir() {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @AfterEach
    public void cleanUpFiles() {
        File directory = new File(DIRECTORY);
        if (directory.exists() && directory.isDirectory()) {
            for(File file: Objects.requireNonNull(directory.listFiles())) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
            directory.delete();
        }
    }

    @Test
    public void testGetExtension() {
        Assertions.assertEquals("txt", FileGenerator.getExtension(FILE));
        Assertions.assertEquals("", FileGenerator.getExtension("file"));

    }

    @Test
    public void testDeleteFile() throws IOException {
        String fileName = "testFile.txt";
        Path newFilePath = Paths.get(DIRECTORY + fileName);
        Files.createFile(newFilePath);

        Assertions.assertTrue(FileGenerator.deleteFile(DIRECTORY + fileName));
        Assertions.assertFalse(FileGenerator.deleteFile(DIRECTORY + fileName));
    }

    @Test
    public void testGenerateFile() throws FileException {
        String newFile = FileGenerator.generateFile(DIRECTORY, "file", "txt");
        Assertions.assertEquals(DIRECTORY + FILE, newFile);
        File file = new File(DIRECTORY + FILE);
        Assertions.assertTrue(file.exists());

        FileException e = Assertions.assertThrows(HandleExtensionException.class, () -> FileGenerator.generateFile(DIRECTORY, "file", "notHandle"));
        Assertions.assertEquals("extension not handled", e.getMessage());

        e = Assertions.assertThrows(PathException.class, () -> FileGenerator.generateFile("no/directory/there", "file", "txt"));
        Assertions.assertEquals("unknown path", e.getMessage());
    }

    @Test
    public void testAnnotation() throws IOException {
        Path newFilePath = Paths.get(DIRECTORY + FILE);
        Files.createFile(newFilePath);
        Model.ModelWithFile model = new Model.ModelWithFile();
        ObjectAnonymizer anonymizer = new ObjectAnonymizer();
        anonymizer.anonymize(model);

        Assertions.assertEquals(DIRECTORY + NEWFILE, model.getValue());

        File file = new File(DIRECTORY + NEWFILE);
        Assertions.assertTrue(file.exists());

        file = new File(DIRECTORY + FILE);
        Assertions.assertFalse(file.exists());
    }

    @Test
    public void testAnnotationWithFalseRemoval() throws IOException {
        Path newFilePath = Paths.get(DIRECTORY + FILE);
        Files.createFile(newFilePath);
        Model.ModelWithOldFileRemove model = new Model.ModelWithOldFileRemove();
        ObjectAnonymizer anonymizer = new ObjectAnonymizer();
        anonymizer.anonymize(model);

        Assertions.assertEquals(DIRECTORY + NEWFILE, model.getValue());

        File file = new File(DIRECTORY + FILE);
        Assertions.assertTrue(file.exists());

        file = new File(DIRECTORY + NEWFILE);
        Assertions.assertTrue(file.exists());
    }

    @Test
    public void testConvert() {
        FileGenerator.generateFile(DIRECTORY + "file.htm");
        File file = new File(DIRECTORY + "file.htm");
        Assertions.assertTrue(file.exists());

        FileGenerator.generateFile(DIRECTORY + "file.jpeg");
        file = new File(DIRECTORY + "file.jpeg");
        Assertions.assertTrue(file.exists());
    }

    @Test
    public void testGenerateFileFromOrigin() {
        FileGenerator.generateFile("src/test/resources/file/base/", DIRECTORY + FILE);
        File file = new File(DIRECTORY + FILE);
        Assertions.assertTrue(file.exists());

        PathException e = Assertions.assertThrows(PathException.class,
            () -> FileGenerator.generateFile("wrong/path/", DIRECTORY + FILE));
        Assertions.assertEquals("unknown path", e.getMessage());

        e = Assertions.assertThrows(PathException.class,
            () -> FileGenerator.generateFile("src/test/resources/file/base/", "wrong/path/file.txt"));
        Assertions.assertEquals("unknown path", e.getMessage());
    }

    @Test
    public void testGenerateByteFromExt() {
        byte[] bytes = FileGenerator.generateFileAsBytes("txt");
        Assertions.assertNotNull(bytes);
    }

    @Test
    public void testGenerateByteFromExtNotHandled() {
        HandleExtensionException e = Assertions.assertThrows(HandleExtensionException.class,
            () -> FileGenerator.generateFileAsBytes("notHandle"));
        Assertions.assertEquals("extension not handled", e.getMessage());
    }

    @Test
    public void testGenerateByteFromExtWithDir() {
        byte[] bytes = FileGenerator.generateFileAsBytes("src/test/resources/file/base/", "txt");
        Assertions.assertNotNull(bytes);
    }

    @Test
    public void testGenerateByteFromWithDirExtNotHandled() {
        PathException e = Assertions.assertThrows(PathException.class,
            () -> FileGenerator.generateFileAsBytes("src/test/resources/file/base/", "notHandle"));
        Assertions.assertEquals("Unable to find the file", e.getMessage());
    }

    @Test
    public void testGenerateFileAsInputStream() {
        InputStream inputStream = FileGenerator.generateFileAsInputStream("txt");
        Assertions.assertNotNull(inputStream);
    }
}
