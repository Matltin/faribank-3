package ir.ac.kntu.db;

import ir.ac.kntu.Loan;

import java.util.ArrayList;

public class LoanRequestDB {

    private ArrayList<Loan> loans;

    public LoanRequestDB() {
        this.loans = new ArrayList<>();
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void remove(Loan loan) {
        loans.remove(loan);
    }
}
