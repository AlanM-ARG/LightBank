package com.mindhub.homebanking.utils;



public final class CardUtils {

    private CardUtils() {

    }
    public static int getRandomNumber3() {
        return (int) (Math.random() * (1000 - 1) + 1);
    }

    public static String getRandomNumberCard() {
        return (int) (Math.random() * (10000 - 1000) + 1000)
                + "-" + (int) (Math.random() * (10000 - 1000) + 1000)
                + "-" + (int) (Math.random() * (10000 - 1000) + 1000)
                + "-" + (int) (Math.random() * (10000 - 1000) + 1000);
    }
}
