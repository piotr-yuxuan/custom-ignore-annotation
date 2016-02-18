package org.piotryuxuan.customignoreannotation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.piotryuxuan.customignoreannotation.AnnotationHandler.newCustomIgnore;
import static org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap.After;
import static org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap.Before;
import static org.piotryuxuan.customignoreannotation.CustomTestRuleTest.step.*;

public class CustomTestRuleTest {

    // This is the subject of the test
    private CustomTestRule subject = null;

    /**
     * These are the steps which can be done or skipped
     */
    public enum step {
        before, base, after
    }

    // In this map we will store the result of an execution.
    private Map<step, Boolean> checks = null;

    // Returns a first-class function which will set a check a true when invoke.
    private final Function<step, Runnable> runnableSupplier = step -> () -> checks.put(step, true);

    // Defines the fake action for the base statement.
    private final Statement baseStatement = new Statement() {
        @Override
        public void evaluate() throws Throwable {
            runnableSupplier.apply(base).run();
        }
    };

    /**
     * Before every test, checks are all reset to false.
     */
    @Before
    public void setUp() {
        checks = Stream.of(
                new SimpleEntry<>(before, false),
                new SimpleEntry<>(base, false),
                new SimpleEntry<>(after, false))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    @Test
    public void test_invokeBothBeforeAndAfter_whenNormalUse() throws Throwable {
        // Given
        subject = new CustomTestRule(runnableSupplier.apply(before), runnableSupplier.apply(after));
        Description description = Description.createSuiteDescription("name", newCustomIgnore());
        // When
        subject.apply(baseStatement, description)
                .evaluate();
        // Then
        assertThat(checks).containsOnly(
                new SimpleEntry<>(before, true),
                new SimpleEntry<>(base, true),
                new SimpleEntry<>(after, true));
    }

    @Test
    public void test_invokeOnlyBefore_whenAfterIgnored() throws Throwable {
        // Given
        subject = new CustomTestRule(runnableSupplier.apply(before), runnableSupplier.apply(after));
        Description description = Description.createSuiteDescription("name", newCustomIgnore(After));
        // When
        subject.apply(baseStatement, description)
                .evaluate();
        // Then
        assertThat(checks).containsOnly(
                new SimpleEntry<>(before, true),
                new SimpleEntry<>(base, true),
                new SimpleEntry<>(after, false));
    }

    @Test
    public void test_invokeOnlyAfter_whenBeforeIgnored() throws Throwable {
        // Given
        subject = new CustomTestRule(runnableSupplier.apply(before), runnableSupplier.apply(after));
        Description description = Description.createSuiteDescription("name", newCustomIgnore(Before));
        // When
        subject.apply(baseStatement, description)
                .evaluate();
        // Then
        assertThat(checks).containsOnly(
                new SimpleEntry<>(before, false),
                new SimpleEntry<>(base, true),
                new SimpleEntry<>(after, true));
    }

    @Test
    public void test_invokeNeitherBeforeNorAfter_whenBothBeforeAndAfterIgnored() throws Throwable {
        // Given
        subject = new CustomTestRule(runnableSupplier.apply(before), runnableSupplier.apply(after));
        Description description = Description.createSuiteDescription("name", newCustomIgnore(After, Before));
        // When
        subject.apply(baseStatement, description)
                .evaluate();
        // Then
        assertThat(checks).containsOnly(
                new SimpleEntry<>(before, false),
                new SimpleEntry<>(base, true),
                new SimpleEntry<>(after, false));
    }
}