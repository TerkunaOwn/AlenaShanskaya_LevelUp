package ru.levelp.at.homework2;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LotteryTicket {

    public static boolean billet(int billetNumber) {
        if (billetNumber < 100000 || billetNumber > 999999) {
            throw new IllegalArgumentException("Ticket not six figures!!!");
        }
        ArrayList<Integer> digits = new ArrayList<>();

        String temp = Integer.toString(billetNumber);
        for (int i = 0; i < temp.length(); i++) {
            String oneChar = Character.toString(temp.charAt(i));
            digits.add(Integer.parseInt(oneChar));
        }
        int firstPart = digits.get(0) + digits.get(1) + digits.get(2);
        int lastPart = digits.get(3) + digits.get(4) + digits.get(5);
        return firstPart == lastPart;
    }
}
