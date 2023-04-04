/*
 *
 */

import org.avisto.anonymization.generator.BooleanGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class BooleanAnonymizationTest {
    @Test
    public void testGenerateBoolean() {
        float minProbabilityWithEpsilon = 0.49f; // epsilon = 0.01 in this case
        float maxProbabilityWithEpsilon = 0.51f; // epsilon = 0.01 in this case

        long size = 100000;
        float effectiveProbability = (float) Stream.generate(BooleanGenerator::generateBoolean).limit(size).filter(value -> value).count() / size;
        Assertions.assertTrue(minProbabilityWithEpsilon <= effectiveProbability && maxProbabilityWithEpsilon >= effectiveProbability);
    }

    @Test
    public void testGenerateBooleanWithProbability() {
        float probability = 0.2f;
        float minProbabilityWithEpsilon = 0.19f; // epsilon = 0.01 in this case
        float maxProbabilityWithEpsilon = 0.21f; // epsilon = 0.01 in this case

        long size = 100000;
        float effectiveProbability = (float) Stream.generate(() -> BooleanGenerator.generateBoolean(probability)).limit(size).filter(value -> value).count() / size;
        Assertions.assertTrue(minProbabilityWithEpsilon <= effectiveProbability && maxProbabilityWithEpsilon >= effectiveProbability);
    }
}
