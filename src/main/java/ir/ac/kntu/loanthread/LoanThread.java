package ir.ac.kntu.loanthread;

import ir.ac.kntu.Loan;
import ir.ac.kntu.Transaction;
import ir.ac.kntu.transaction.TransactionType;
import ir.ac.kntu.user.customer.Customer;

public class LoanThread implements Runnable{
    private Loan loan;
    private Customer customer;

    public void setInfo(Customer customer, Loan loan){
        this.customer = customer;
        this.loan = loan;
    }

    @Override
    public void run() {
        if(customer.getLoanDB().checkGetLoan()) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            customer.addLoan(loan);
            customer.getLoanRequestDB().remove(loan);
            Transaction transaction = new Transaction(customer.getFirstName(), customer.getLastName(), customer.getAccount().getAccountNO(),
                    customer.getAccount().getAccountNO(), TransactionType.LOAN_RECEIVE, loan.getMoneyPerPayment());
            customer.getAccount().getTransactionDB().addTransaction(transaction);
        }
    }
}
