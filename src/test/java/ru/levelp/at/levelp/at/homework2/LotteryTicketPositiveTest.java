package ru.levelp.at.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.levelp.at.homework2.LotteryTicket;

public class LotteryTicketPositiveTest {

    @Tag("Pozitive")
    @ParameterizedTest
    @ValueSource(ints = {111111, 222222})

    void sumGood(int input) {
        boolean expected = true;
        boolean actual = LotteryTicket.billet(input);
        assertThat(actual).isEqualTo(expected);
    }

    @Tag("Pozitive")
    @ParameterizedTest
    @ValueSource(ints = {11111, 22222, })

    void billetIsBad(int input) {
        boolean expected = false;
        boolean actual = LotteryTicket.billet(input);
        assertThat(actual).isEqualTo(expected);
    }

    @Tag("Pozitive")
    @ParameterizedTest
    @ValueSource(ints = {111112, 222223})

    void sumIsNotGood(int input) {
        boolean expected = false;
        boolean actual = LotteryTicket.billet(input);
        assertThat(actual).isEqualTo(expected);
    }
}
