package ir.ac.kntu.db;

import ir.ac.kntu.Constance;
import ir.ac.kntu.Loan;
import ir.ac.kntu.Message;
import ir.ac.kntu.loan.LoanType;
import ir.ac.kntu.message.MessageOption;
import ir.ac.kntu.user.customer.Customer;
import ir.ac.kntu.user.customer.State;
import ir.ac.kntu.util.ScannerWrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomerDB {
    private static Set<Customer> customers;

    public CustomerDB(SimCardDB simCardDB) {
        customers = new HashSet<>();
        Customer customer1 = new Customer("a", "a", "Mm@1383", "1", "1", SimCardDB.getPhones());
        Customer customer2 = new Customer("b", "b", "Rr@1384", "13", "09112607040", SimCardDB.getPhones());
        Customer customer3 = new Customer("c", "c", "Cc@1383", "14", "09122607040", SimCardDB.getPhones());

        customer1.getMessageDB().addMessage(new Message("123", "123123", MessageOption.REPORT));

        customer1.getLoanDB().addLoan(new Loan(1000, 1, 1000, LoanType.LONG_TIME));
        customer1.getLoanDB().addLoan(new Loan(1000, 10, 100, LoanType.LONG_TIME));

        customer1.getAccount().setAccountNO("123");
        customer2.getAccount().setAccountNO("456");

        customer1.setState(State.ACCEPTED);
        customer2.setState(State.ACCEPTED);
        customer3.setState(State.ACCEPTED);

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);

    }

    public static Set<Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(Set<Customer> customers) {
        CustomerDB.customers = customers;
    }

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static void removeCustomer(Customer customer) {
        try {
            customers.remove(customer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }



    public static Customer findCustomerByAccountNO(String accountNO) {
        for (Customer customer : customers) {
            if (customer.getAccount().getAccountNO().equals(accountNO)) {
                return customer;
            }
        }
        return null;
    }

    public Customer findCustomerByPhone(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhone().getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    public boolean doesExist(Customer customer) {
        return customers.contains(customer);
    }

    public int size() {
        return customers.size();
    }

    public static String getAccountNumber(String cardNumber) {
        for (Customer customer : customers) {
            if (customer.getAccount().getCard().getCardNumber().equals(cardNumber)) {
                return customer.getAccount().getAccountNO();
            }
        }
        return null;
    }

    public void printCustomer() {
        Map<Integer, Customer> map = getMap();
        int size = map.size();
        int valueToDisPlay = Constance.VALUE_TO_DISPLAY;
        if (valueToDisPlay > size) {
            valueToDisPlay = size;
        }
        int currentPosition = 1;
        String inputStr;
        print(1, valueToDisPlay + 1, map);
        do {
            inputStr = ScannerWrapper.getInstance().nextLine();
            switch (inputStr) {
                case "next" -> currentPosition = plus(currentPosition, size, valueToDisPlay, map);
                case "back" -> currentPosition = minus(currentPosition, size, -valueToDisPlay, map);
                case "quit" -> {
                    return;
                }
                default -> System.out.println("invalid input");
            }
        } while (true);
    }

    private Map<Integer, Customer> getMap() {
        Map<Integer, Customer> map = new HashMap<>();
        int counter = 1;
        for (Customer customer : customers) {
            map.put(counter, customer);
            counter++;
        }
        return map;
    }

    private int minus(int currentPosition, int size, int amount, Map<Integer, Customer> map) {
        if (currentPosition + amount < 0) {
            currentPosition = 0;
            print(1, -amount + 1, map);
        } else {
            if (currentPosition == size) {
                currentPosition += amount;
            }
            if (currentPosition + amount < 1) {
                currentPosition = 0;
                print(1, -amount + 1, map);
                return currentPosition;
            }
            print(currentPosition + amount, currentPosition + 1, map);
            currentPosition += amount;
        }
        return currentPosition;
    }

    private int plus(int currentPosition, int size, int amount, Map<Integer, Customer> map) {
        if (currentPosition + amount > size) {
            currentPosition = size;
            print(size - amount + 1, size + 1, map);
        } else {
            if (currentPosition == 1) {
                currentPosition += amount;
            }
            if (currentPosition + amount > size) {
                currentPosition = size;
                print(size - amount, size, map);
                return currentPosition;
            }
            print(currentPosition, currentPosition + amount, map);
            currentPosition += amount;
        }
        return currentPosition;
    }


    private void print(int first, int second, Map<Integer, Customer> map) {
        for (int i = first; i < second; i++) {
            System.out.println(i + "." + map.get(i).getFirstName() + " " + map.get(i).getLastName() + " " + map.get(i).getPhoneNumber());
        }
    }
}
