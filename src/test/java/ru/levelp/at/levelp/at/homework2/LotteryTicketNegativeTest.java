package ru.levelp.at.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.levelp.at.homework2.LotteryTicket;

public class LotteryTicketNegativeTest {

    @Tag("Negative")
    @ParameterizedTest
    @ValueSource(ints = {1111})

    void billetIsBad(int input) {
        boolean expected = false;
        boolean actual = LotteryTicket.billet(input);
        assertThat(actual).isEqualTo(expected);
    }
}
