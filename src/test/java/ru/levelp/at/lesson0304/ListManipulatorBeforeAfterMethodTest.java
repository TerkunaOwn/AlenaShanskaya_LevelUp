package ru.levelp.at.lesson0304;

import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class ListManipulatorBeforeAfterMethodTest {

    private ListManipulator manipulator; //принициализироали переменную

    @BeforeMethod //hook. hook до
    public void beforeMethod() {
        System.out.println("beforeMethod");
        manipulator = new ListManipulator(); // инициализация метода
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

    @AfterMethod //hook. hook после
    public void afterMethod() {
        System.out.println("afterMethod");
        manipulator = null;
    }
}
