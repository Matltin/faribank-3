package ir.ac.kntu.faribank.card;

import java.util.Objects;
import java.util.Random;

public class Card {
    private String password;
    private String cardNumber;

    public Card() {
        password = randomPassword();
        cardNumber = randomCarNumber();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    private String randomPassword() {
        Random random = new Random();
        return String.valueOf(random.nextInt(1000,10000));
    }

    private String randomCarNumber() {
        Random random = new Random();
        return "9610" + String.valueOf(random.nextInt((int)Math.pow(10,8), (int)Math.pow(10,9)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Card card = (Card) obj;
        return Objects.equals(cardNumber, card.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cardNumber);
    }
}
