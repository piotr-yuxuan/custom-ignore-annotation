package org.piotryuxuan.customignoreannotation;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Arrays;

import static org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap.After;
import static org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap.Before;

public class CustomTestRule implements TestRule {

    Runnable setUp, tearDown;

    /**
     * Represents the function identity: does nothing.
     */
    public static final Runnable identity = () -> {
    };

    /**
     * Constructs a new test rule with two arguments. If you don't want to specify any of the arguments,
     * you can use the static identity function provided.
     *
     * @param setUp    the runnable to be run before each test method.
     * @param tearDown the runnable to be run after each test method.
     */
    public CustomTestRule(Runnable setUp, Runnable tearDown) {
        this.setUp = setUp;
        this.tearDown = tearDown;
    }

    private boolean predicate(Description description, CustomIgnore.CustomWrap layer) {
        return description.getAnnotation(CustomIgnore.class) == null
                || !Arrays.asList(description.getAnnotation(CustomIgnore.class).value()).contains(layer);
    }

    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (predicate(description, Before)) setUp.run();
                else System.out.println("setUp function skipped");

                base.evaluate();

                if (predicate(description, After)) tearDown.run();
                else System.out.println("tearDown function skipped");
            }
        };
    }
}