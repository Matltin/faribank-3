package ir.ac.kntu.faribank.account;

import ir.ac.kntu.Box;
import ir.ac.kntu.Constance;
import ir.ac.kntu.Transaction;
import ir.ac.kntu.box.BoxType;
import ir.ac.kntu.db.*;
import ir.ac.kntu.faribank.card.Card;
import ir.ac.kntu.paya.Paya;
import ir.ac.kntu.transaction.TransactionType;
import ir.ac.kntu.user.customer.Customer;

import java.util.ArrayList;
import java.util.Objects;

public class Account {

    private long balance;
    private String accountNO;
    private TransactionDB transactionDB;
    private BoxDB boxDB;
    private Card card;

    public Account(long balance, String accountNO) {
        this.balance = balance;
        this.accountNO = accountNO;
        transactionDB = new TransactionDB();
        card = new Card();
        boxDB = new BoxDB(new ArrayList<>());
        addDefaultBox();
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountNO() {
        return accountNO;
    }

    public void setAccountNO(String accountNO) {
        this.accountNO = accountNO;
    }

    public Card getCard() {
        return card;
    }

    public BoxDB getBoxDB() {
        return boxDB;
    }

    public void setBoxDB(BoxDB boxDB) {
        this.boxDB = boxDB;
    }

    public void increaseCredit(long inputMoney) {
        setBalance(getBalance() + inputMoney);
        Customer customer = CustomerDB.findCustomerByAccountNO(accountNO);
        Transaction transaction = new Transaction(customer.getFirstName(), customer.getLastName(), customer.getAccount().getAccountNO(), getAccountNO(), TransactionType.INCREASE_CREDIT, inputMoney);
        transactionDB.addTransaction(transaction);
    }

    public TransactionDB getTransactionDB() {
        return transactionDB;
    }

    public void setTransactionDB(TransactionDB transactionDB) {
        this.transactionDB = transactionDB;
    }

    public boolean transferMoney(long inputMoney, long moneyWithWage, String accountNO) {
        try {
            if (moneyWithWage <= balance) {
                setBalance(getBalance() - moneyWithWage);
                transferMoneyToCustomer(inputMoney, accountNO);
                return true;
            } else {
                throw new RuntimeException("input money : " + inputMoney + " more than your balance!!");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean transferFari(long inputMoney, String accountNO) {
        if(inputMoney > 8000000) {
            System.out.println(Constance.RED + "payment is out of limit!!" + Constance.RESET);
            return false;
        }
        if(inputMoney + Constance.getFariFariWage() > getBalance()) {
            System.out.println(Constance.RED + "input money is more than your balance!!" + Constance.RESET);
            return false;
        }
        setBalance(getBalance() - inputMoney - Constance.getFariFariWage());
        Customer customer = CustomerDB.findCustomerByAccountNO(accountNO);
        customer.getAccount().setBalance(inputMoney + customer.getAccount().getBalance());
        Transaction transaction = new Transaction(customer.getFirstName(), customer.getLastName(), customer.getAccount().getAccountNO(), getAccountNO(), TransactionType.TRANSFER, inputMoney);
        transactionDB.addTransaction(transaction);
        roundBalance();
        return true;
    }

    public boolean transferPole(long inputMoney, String accountNO) {
        if(inputMoney > 5000000) {
            System.out.println(Constance.RED + "payment is out of limit!!" + Constance.RESET);
            return false;
        }
        long finalMoney = inputMoney + (inputMoney * Constance.getFariPole())/100;
        if(inputMoney + Constance.getFariPole() > getBalance()) {
            System.out.println(Constance.RED + "input money is more than your balance!!" + Constance.RESET);
            return false;
        }
        setBalance(getBalance() - finalMoney);
        Customer customer = BankDB.findCustomerByAccNumber(accountNO);
        customer.getAccount().setBalance(inputMoney + customer.getAccount().getBalance());
        Transaction transaction = new Transaction(customer.getFirstName(), customer.getLastName(), customer.getAccount().getAccountNO(), getAccountNO(), TransactionType.TRANSFER, inputMoney);
        transactionDB.addTransaction(transaction);
        roundBalance();
        return true;
    }

    public boolean transferPaya(long inputMoney, Customer customer, Customer cust) {
        if(inputMoney > 5000000) {
            System.out.println(Constance.RED + "payment is out of limit!!" + Constance.RESET);
            return false;
        }
        if(inputMoney + Constance.getFariPole() > getBalance()) {
            System.out.println(Constance.RED + "input money is more than your balance!!" + Constance.RESET);
            return false;
        }
        Paya paya = new Paya(customer, cust, inputMoney);
        PayaDB.addPaya(paya);
        return true;
    }

    public void withdraw(long inputMoney) {
        setBalance(getBalance() - inputMoney);
    }

    public void deposit(long inputMoney) {
        setBalance(getBalance() + inputMoney);
    }

    public void roundBalance() {
        int length = String.valueOf(balance).length();
        int size = (length*3)/4;
        long remain = balance % (long) Math.pow(10, size);
        long finalNumber = (long) Math.pow(10, size) - remain;
        withdraw(finalNumber);
        Box box = boxDB.findBox("defaultRemaining");
        box.deposit(finalNumber);
    }

    private void transferMoneyToCustomer(long money, String accountNO) {
        Customer customer = BankDB.findCustomerByAccNumber(accountNO);
        customer.getAccount().setBalance(money + customer.getAccount().getBalance());
        Transaction transaction = new Transaction(customer.getFirstName(), customer.getLastName(), customer.getAccount().getAccountNO(), getAccountNO(), TransactionType.TRANSFER, money);
        transactionDB.addTransaction(transaction);
        roundBalance();
    }

    private void addDefaultBox() {
        boxDB.addBox(new Box("defaultRemaining", 0, BoxType.REMAINING));
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", accountNO='" + accountNO + '\'' +
                ", transactionDB=" + transactionDB +
                ", card=" + card +
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
        Account account = (Account) obj;
        return Objects.equals(accountNO, account.accountNO) && Objects.equals(card, account.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNO, card);
    }
}
