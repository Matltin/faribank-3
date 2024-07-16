package ir.ac.kntu.db;

import ir.ac.kntu.ContactPerson;
import ir.ac.kntu.simcard.SimCard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SimCardTransactionDB {

    private List<SimCard> simCards;

    public SimCardTransactionDB() {
        this.simCards = new ArrayList<>();
    }

    public void addSim(SimCard simCard) {
        simCards.add(simCard);
    }

    public void removeSim(SimCard simCard) {
        simCards.remove(simCard);
    }

    public List<SimCard> getSimCards() {
        return simCards;
    }

    public void setSimCards(List<SimCard> simCards) {
        this.simCards = simCards;
    }

    public void printTransaction() {
        int counter = 1;
        for(SimCard simCard : simCards) {
            System.out.println(counter + "." + simCard);
            counter++;
        }
    }
}
