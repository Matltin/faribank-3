package ir.ac.kntu.customerthread;

import ir.ac.kntu.user.customer.Customer;
import ir.ac.kntu.user.customer.State;

public class CustomerThread implements Runnable{

    private Customer customer;

    public void setInfo(Customer customer){
        this.customer = customer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        customer.setState(State.ACCEPTED);
    }
}
