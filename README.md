# Custom ignore annotation

Content of this repository:

```
.
├── LICENSE.txt
├── pom.xml
├── README.md
└── src
    ├── main/java/org/piotryuxuan/customignoreannotation
    │   ├── CustomIgnore.java
    │   ├── CustomTestRule.java
    │   └── example
    │       └── ExampleTest.java
    └── test/java/org/piotryuxuan/customignoreannotation
        ├── AnnotationHandler.java
        └── CustomTestRuleTest.java
```

## The problem

When you write Java unit tests, you may annotate some methods as set-up
and tear-down by using annotations `@Before` and `@After` so they are to
be run before and after every test methods. However, sometimes you need
some test method not to run between the two first ones.

For example, if you use a mock controller and call `control.verify` in
the method to be run after each test, a test which doesn't use this
controller (hence doesn't invoke `control.replay()`) will
fails. Sometimes it's more convenient not to change all other tests for
one which is a bit different but simply not to invoke the faultu method
around it.

## Simple proposal for a solution

This repository aims at suggesting a simple way to ignore set-up and
tear-down methods for some tests. It is very easy to use:

```Java
@Test
public void test_everythingIsExecuted() throws Exception {
    System.out.println("Body of the test");
}

@Test
@CustomIgnore(Before)
public void test_beforeIsIgnored() throws Exception {
    System.out.println("Body of the test");
}
```

A more thoroughful example is to be found in `ExampleTest.java`

## Technical details

 * As this repository is about unit tests, of course its content is
unit-tested itself.
 * This piece of code uses Java 8 features (first-class functions,
   functional interfaces)
 * It relies on custom annotations.

## Improvements

Please feel free to fork this repository or suggest better ways to
improve any part of it.

## License

Copyright © 2016 胡雨軒

Distributed under the General Public License either version 3.0 or (at
your option) any later version.
