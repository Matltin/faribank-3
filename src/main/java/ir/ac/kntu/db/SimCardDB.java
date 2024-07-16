package ir.ac.kntu.db;

import ir.ac.kntu.phone.Phone;

import java.util.HashSet;
import java.util.Set;

public class SimCardDB {
    private static Set<Phone> phones;

    public SimCardDB() {
        phones = new HashSet<>();
    }

    public static Set<Phone> getPhones() {
        return phones;
    }

    public static void addPhone(Phone phone) {
        phones.add(phone);
    }

    public static void remove(Phone phone) {
        phones.remove(phone);
    }

    public static boolean contain(Phone phone) {
        return phones.contains(phone);
    }

    public void setPhones(Set<Phone> phones) {
        SimCardDB.phones = phones;
    }

    public static Phone findPhone(String phoneNumber) {
        for(Phone phone : phones) {
            if(phone.getPhoneNumber().equals(phoneNumber)) {
                return phone;
            }
        }
        return null;
    }
}
