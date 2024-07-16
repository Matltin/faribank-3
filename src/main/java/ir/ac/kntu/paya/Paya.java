package ir.ac.kntu.paya;

import ir.ac.kntu.user.customer.Customer;

public class Paya {
    private Customer sourceCustomer;
    private Customer dstCustomer;
    private long money;

    public Paya(Customer sourceCustomer, Customer dstCustomer, long money) {
        this.sourceCustomer = sourceCustomer;
        this.dstCustomer = dstCustomer;
        this.money = money;
    }

    public Customer getSourceCustomer() {
        return sourceCustomer;
    }

    public void setSourceCustomer(Customer sourceCustomer) {
        this.sourceCustomer = sourceCustomer;
    }

    public Customer getDstCustomer() {
        return dstCustomer;
    }

    public void setDstCustomer(Customer dstCustomer) {
        this.dstCustomer = dstCustomer;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
