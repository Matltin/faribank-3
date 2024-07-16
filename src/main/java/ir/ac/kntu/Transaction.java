package ir.ac.kntu;

import ir.ac.kntu.transaction.TransactionType;
import ir.ac.kntu.util.Calendar;

import java.util.Date;

public class Transaction {

    private String fNameDestination;
    private String lNameDestination;
    private Date date;
    private String formatDate;
    private String dstAccNO;
    private String sourceAccountNO;
    private long money;
    private TransactionType transactionType;
    private String followupNumber;    // شناره پیگیری

    public Transaction(String fNameDestination, String lNameDestination,
                       String dstAccNO, String sourceAccountNO, TransactionType transactionType, long money) {
        this.fNameDestination = fNameDestination;
        this.lNameDestination = lNameDestination;
        this.date = Calendar.getDate();
        this.formatDate = Calendar.getDateFormat(date);
        this.dstAccNO = dstAccNO;
        this.sourceAccountNO = sourceAccountNO;
        this.followupNumber = getRandom();
        this.transactionType = transactionType;
    }

    public Transaction(Transaction transaction) {
        this(transaction.fNameDestination, transaction.lNameDestination, transaction.dstAccNO,
                transaction.sourceAccountNO, transaction.transactionType, transaction.money);
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Date getDate() {
        return date;
    }

    private String getRandom() {
        int max = 9999, min = 1000;
        int number = min + (int) (Math.random() * ((max - min) + 1));
        return String.valueOf(number);
    }

    public String getfNameDestination() {
        return fNameDestination;
    }

    public void setfNameDestination(String fNameDestination) {
        this.fNameDestination = fNameDestination;
    }

    public String getlNameDestination() {
        return lNameDestination;
    }

    public void setlNameDestination(String lNameDestination) {
        this.lNameDestination = lNameDestination;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public String getDstAccNO() {
        return dstAccNO;
    }

    public void setDstAccNO(String dstAccNO) {
        this.dstAccNO = dstAccNO;
    }

    public String getSourceAccountNO() {
        return sourceAccountNO;
    }

    public void setSourceAccountNO(String sourceAccountNO) {
        this.sourceAccountNO = sourceAccountNO;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getFollowupNumber() {
        return followupNumber;
    }

    public void setFollowupNumber(String followupNumber) {
        this.followupNumber = followupNumber;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "fNameDestination='" + fNameDestination + '\'' +
                ", lNameDestination='" + lNameDestination + '\'' +
                ", date=" + date +
                ", formatDate='" + formatDate + '\'' +
                ", dstAccNO='" + dstAccNO + '\'' +
                ", sourceAccountNO='" + sourceAccountNO + '\'' +
                ", money=" + money +
                ", transactionType=" + transactionType +
                ", followupNumber='" + followupNumber + '\'' +
                '}';
    }
}
