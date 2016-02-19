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

When you write Java unit tests, you may annotate some methods with
`@Before` and `@After` so they are to be run before and after every test
methods. Let's say these are wrapping methods.

Sometimes you need some test method not to run between these wrapping
methods. For example, if you use a mock controller and call
`control::verify` in the method to be run after each test, a test which
doesn't use this controller (hence doesn't invoke `control::replay`)
will fails. Sometimes it's more convenient not to change all other tests
for one which is a bit different but simply not to invoke the faultu
method around it.

The easiest solution is to split tests in different files and set up the
wrapping methods you need for each of them. However, some project
architectures gather all tests for a given Java file `AwesomeClass.java`
into a single file `AwesomeClassTest.java`. Either you do some trick to
deal with the controller, either you just ignore the failing wrapping
functions for this very test method.

## Simple proposal for a solution

This repository aims at suggesting a simple way to ignore set-up and
tear-down methods for some tests. A more thoroughful example is to be
found in
[`ExampleTest.java`](https://github.com/piotr-yuxuan/custom-ignore-annotation/blob/master/src/main/java/org/piotryuxuan/customignoreannotation/example/ExampleTest.java).
It is very easy to use:

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
