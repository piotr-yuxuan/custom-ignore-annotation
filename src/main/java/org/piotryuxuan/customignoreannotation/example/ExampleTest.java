package org.piotryuxuan.customignoreannotation.example;

import org.junit.Rule;
import org.junit.Test;
import org.piotryuxuan.customignoreannotation.CustomIgnore;
import org.piotryuxuan.customignoreannotation.CustomTestRule;

import static org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap.After;
import static org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap.Before;

public class ExampleTest {

    // Because of forbidden forward reference, fields must be declared before.
    // However, plain method can be declared anywhere.
    private Runnable setUp = () -> System.out.println("Before a test");

    @Rule
    // It's OK for custom functions to be private as the reference is passed from within this class.
    public CustomTestRule customTestRule = new CustomTestRule(this.setUp, this::tearDown);

    private void tearDown() {
        System.out.println("After a test");
    }

    @Test
    public void test_everythingIsExecuted() throws Exception {
        System.out.println("Body of the test");
    }

    @Test
    @CustomIgnore(Before)
    public void test_beforeIsIgnored() throws Exception {
        System.out.println("Body of the test");
    }

    @Test
    @CustomIgnore(After)
    public void test_afterIsIgnored() throws Exception {
        System.out.println("Body of the test");
    }

    @Test
    @CustomIgnore({Before, After})
    public void test_beforeAndAfterAreIgnored() throws Exception {
        System.out.println("Body of the test");
    }
}