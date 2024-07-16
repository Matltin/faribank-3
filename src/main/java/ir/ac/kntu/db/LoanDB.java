package ir.ac.kntu.db;

import ir.ac.kntu.Loan;

import java.util.ArrayList;

public class LoanDB {

    private ArrayList<Loan> loans;

    public LoanDB() {
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

    public boolean checkGetLoan() {
        int counter = 0;
        for(Loan loan : loans) {
            for(int i: loan.getMap().keySet()) {
                if(loan.getMap().get(i) > 5) {
                    counter++;
                }
            }
        }
        return counter < 5;
    }

    public Loan findLoan(int id) {
        for(Loan loan : loans) {
            if (loan.getId() == id) {
                return loan;
            }
        }
        return null;
    }
}
