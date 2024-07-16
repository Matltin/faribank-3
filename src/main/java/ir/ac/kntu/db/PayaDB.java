package ir.ac.kntu.db;

import ir.ac.kntu.paya.Paya;

import java.util.ArrayList;
import java.util.List;

public class PayaDB {
    private static List<Paya> payas;

    public PayaDB() {
        payas = new ArrayList<>();
    }

    public static void addPaya(Paya paya) {
        payas.add(paya);
    }

    public void removePaya(Paya paya) {
        payas.remove(paya);
    }

    public int size() {
        return payas.size();
    }

    public Paya getIndex(int index) {
        return payas.get(index);
    }

    public List<Paya> getPayas() {
        return payas;
    }

    public void setPayas(List<Paya> payas) {
        this.payas = payas;
    }

    public void printPayaDB() {
        int counter = 1;
        for (Paya paya : payas) {
            System.out.println(counter + "." + " source customer : " + paya.getSourceCustomer().getLastName() +
                    " destination customer : " + paya.getDstCustomer().getLastName());
            counter++;
        }
    }
}
