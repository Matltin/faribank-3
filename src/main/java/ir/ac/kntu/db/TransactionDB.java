package ir.ac.kntu.db;

import ir.ac.kntu.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TransactionDB {
    private List<Transaction> transactions;

    public TransactionDB() {
        transactions = new LinkedList<>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int size() {
        return transactions.size();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void printTransactions() {
        int counter = 1;
        for (Transaction transaction : transactions) {
            System.out.println(counter + "." + transaction);
            counter++;
        }
    }

    @Override
    public String toString() {
        return "TransactionDB{" +
                "transactions=" + transactions +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TransactionDB that = (TransactionDB) obj;
        return Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transactions);
    }
}
