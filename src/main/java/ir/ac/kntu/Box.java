package ir.ac.kntu;

import ir.ac.kntu.box.BoxType;

import java.util.Date;
import java.util.Objects;

public class Box {
    private String name;
    private long balance;
    private BoxType boxType;
    private Date date;
    private int countMonth;

    public Box(String name, long balance, BoxType boxType) {
        this.name = name;
        this.balance = balance;
        this.boxType = boxType;
        date = new Date();
    }

    public Box(String name, long balance, BoxType boxType, int countMonth) {
        this.name = name;
        this.balance = balance;
        this.boxType = boxType;
        this.countMonth = countMonth;
        date = new Date();
    }

    public Box(Box box) {
        this(box.name, box.balance, box.boxType, box.countMonth);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public BoxType getBoxType() {
        return boxType;
    }

    public void makeNewDate() {
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCountMonth() {
        return countMonth;
    }

    public void setCountMonth(int countMonth) {
        this.countMonth = countMonth;
    }

    public void withdraw(long inputMoney) {
        setBalance(getBalance() - inputMoney);
    }

    public void deposit(long inputMoney) {
        setBalance(getBalance() + inputMoney);
    }

    @Override
    public String toString() {
        return "Box{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", boxType=" + boxType +
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
        Box box = (Box) obj;
        return Objects.equals(name, box.name) && boxType == box.boxType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, boxType);
    }
}
