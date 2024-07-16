package ir.ac.kntu.db;

import ir.ac.kntu.user.customer.Customer;

import java.util.HashSet;
import java.util.Set;

public class BankDB {
    private static Set<Customer> bankCustomer;

    public BankDB(SimCardDB simCardDB) {
        bankCustomer = new HashSet<>();
        Customer customer1 = new Customer("jack", "valobiyayesehramiz", "Jj@1980", "91", "09052607040", SimCardDB.getPhones());
        Customer customer2 = new Customer("mmd", "valtopesehramiz", "Mm@1376", "55", "09092607040", SimCardDB.getPhones());

        customer1.getAccount().setAccountNO("123456789");
        customer1.getAccount().getCard().setCardNumber("123456789123");

        customer2.getAccount().setAccountNO("123456798");
        customer2.getAccount().getCard().setCardNumber("123456789132");

        bankCustomer.add(customer1);
        bankCustomer.add(customer2);

    }

    public static Set<Customer> getBankCustomer() {
        return bankCustomer;
    }

    public static void setBankCustomer(Set<Customer> bankCustomer) {
        BankDB.bankCustomer = bankCustomer;
    }

    public static void addCustomer(Customer customer) {
        bankCustomer.add(customer);
    }

    public void removeCustomer(Customer customer) {
        bankCustomer.remove(customer);
    }

    public static boolean contain(Customer customer) {
        return bankCustomer.contains(customer);
    }

    public static Customer findCustomerByAccNumber(String accountNO) {
        for(Customer customer : bankCustomer) {
            if(customer.getAccount().getAccountNO().equals(accountNO)) {
                return customer;
            }
        }
        return null;
    }

    public Customer findCustomerByPhone(String phoneNumber) {
        for (Customer customer : bankCustomer) {
            if(customer.getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    public static String getAccountNumber(String cardNumber) {
        for(Customer customer : bankCustomer) {
            if(customer.getAccount().getCard().getCardNumber().equals(cardNumber)) {
                return customer.getAccount().getAccountNO();
            }
        }
        return null;
    }

    public void printBankDB() {
        int counter = 1;
        for(Customer customer : bankCustomer) {
            System.out.println(counter + "." + customer.getFirstName() + customer.getLastName());
            counter++;
        }
    }
}
