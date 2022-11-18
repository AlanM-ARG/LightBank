package com.mindhub.homebanking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mindhub.homebanking.utils.CardUtils.getRandomNumber3;
import static com.mindhub.homebanking.utils.CardUtils.getRandomNumberCard;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = getRandomNumberCard();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardNumberIsString(){
        String cardNumber = getRandomNumberCard();
        assertThat(cardNumber, isA(String.class));
    }

    @Test
    public void cardCvvIsInt(){
        int cvvNumber = getRandomNumber3();
        assertThat(cvvNumber,isA(int.class));
    }

    @Test
    public void cardCvvIsCreated(){
        int cvvNumber = getRandomNumber3();
        assertThat(cvvNumber, notNullValue(int.class));
    }

}
