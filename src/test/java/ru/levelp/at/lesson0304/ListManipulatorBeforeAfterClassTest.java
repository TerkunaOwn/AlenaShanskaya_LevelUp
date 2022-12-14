package ru.levelp.at.lesson0304;

import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class ListManipulatorBeforeAfterClassTest {
    private ListManipulator manipulator;

    @BeforeClass //действий до и после класса. будут выполнять до и после класса, который мы определили
    public void beforeClass() {
        System.out.println("beforeClass");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("beforeMethod");
        manipulator = new ListManipulator();
    }

    @Test
    public void manipulateTest() {
        System.out.println("manipulateTest");
        List<String> input = List.of("send", "close", "Sam", "rock");
        List<String> expected = List.of("end", "am", "cloe", "rock");
        List<String> actual = manipulator.manipulate(input, "s");

        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void manipulateOneMoreTest() {
        System.out.println("manipulateOneMoreTest");
        List<String> input = List.of("Send", "close", "SaM", "rOck", "SssSSlkSSsss");
        List<String> expected = List.of("end", "aM", "cloe", "rOck", "lk");
        List<String> actual = manipulator.manipulate(input, "s");

        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("afterMethod");
        manipulator = null;
    }

    @AfterClass
    public void afterClass() {
        System.out.println("afterClass");
        manipulator = null;
    }
}
