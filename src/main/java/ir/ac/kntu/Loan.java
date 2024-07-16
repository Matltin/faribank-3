package ir.ac.kntu;

import ir.ac.kntu.loan.LoanStatus;
import ir.ac.kntu.loan.LoanType;
import ir.ac.kntu.util.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Loan {
    private static int id = 0;
    private long loanMoney;
    private int month;
    private long moneyPerPayment;
    private int monthExist;
    private int installmentNo;
    private LoanType loanType;
    private Map<Integer, Double> map;
    private Date date;
    private LoanStatus status;

    public Loan(long loanMoney, int month, long moneyPerPayment, LoanType loanType) {
        id++;
        this.loanMoney = loanMoney;
        this.month = month;
        monthExist = this.month;
        this.moneyPerPayment = moneyPerPayment;
        this.loanType = loanType;
        date = Calendar.getDate();
        map = new HashMap<>();
        status = LoanStatus.IN_PROGRESSING;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Loan(Loan loan) {
        this(loan.loanMoney, loan.month, loan.moneyPerPayment, loan.loanType);
        id--;
    }

    public int getInstallmentNo() {
        installmentNo = map.size();
        return installmentNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        Loan.id = id;
    }

    public void setInstallmentNo(int installmentNo) {
        this.installmentNo = installmentNo;
    }

    public long getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(long loanMoney) {
        this.loanMoney = loanMoney;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void releaseMonth() {
        month--;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public long getMoneyPerPayment() {
        return moneyPerPayment;
    }

    public void setMoneyPerPayment(long moneyPerPayment) {
        this.moneyPerPayment = moneyPerPayment;
    }

    public Map<Integer, Double> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Double> map) {
        this.map = map;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean pay() {
        monthExist--;

        long startedDate = date.getTime();
        long nowDate = Calendar.getDate().getTime();
        double number = ((double) (startedDate - nowDate) / Constance.MILE_SECOND) - (getInstallmentNo());
        map.put(month-monthExist, number);

        return monthExist == 0;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanMoney=" + loanMoney +
                ", month=" + month +
                ", moneyPerPayment=" + moneyPerPayment +
                ", monthExist=" + monthExist +
                ", loanType=" + loanType +
                '}';
    }
}
