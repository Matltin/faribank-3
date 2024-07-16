package ir.ac.kntu.db;

import ir.ac.kntu.Constance;
import ir.ac.kntu.ContactPerson;
import ir.ac.kntu.util.ScannerWrapper;

import java.util.*;

public class ContactPersonDB {

    private List<ContactPerson> contactPersonList;

    public ContactPersonDB() {
        this.contactPersonList = new ArrayList<>();
    }

    public List<ContactPerson> getContactPerson() {
        return contactPersonList;
    }

    public void setContactPerson(List<ContactPerson> contactPerson) {
        this.contactPersonList = contactPerson;
    }

    public void addContactPerson(ContactPerson contactPerson) {
        contactPersonList.add(contactPerson);
    }

    public ContactPerson findPerson(String phoneNumber) {
        for (ContactPerson contactPerson : contactPersonList) {
            if (contactPerson.getPhoneNumber().equals(phoneNumber)) {
                return contactPerson;
            }
        }
        return null;
    }

    public void removePerson(ContactPerson contactPerson) {
        try {
            if (doesExist(contactPerson)) {
                contactPersonList.remove(contactPerson);
            } else {
                throw new RuntimeException("contact not found!!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean doesExist(ContactPerson contactPerson) {
        return contactPersonList.contains(contactPerson);
    }

    public void printContactPerson() {
        Map<Integer, ContactPerson> map = getMap();
        int size = map.size();
        int valueToDisPlay = Constance.VALUE_TO_DISPLAY;
        if(valueToDisPlay > size) {
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
        } while(true);
    }

    private Map<Integer, ContactPerson> getMap() {
        Map<Integer, ContactPerson> map = new HashMap<>();
        int counter = 1;
        for(ContactPerson contactPerson : contactPersonList) {
            map.put(counter, contactPerson);
            counter++;
        }
        return map;
    }

    private int minus(int currentPosition, int size, int amount, Map<Integer, ContactPerson> map) {
        if(currentPosition + amount < 0) {
            currentPosition = 0;
            print(1, -amount + 1, map);
        } else {
            if(currentPosition == size) {
                currentPosition += amount;
            }
            if(currentPosition + amount < 1) {
                currentPosition = 0;
                print(1, -amount + 1, map);
                return currentPosition;
            }
            print(currentPosition + amount, currentPosition + 1, map);
            currentPosition += amount;
        }
        return currentPosition;
    }

    private int plus(int currentPosition, int size, int amount, Map<Integer, ContactPerson> map) {
        if(currentPosition + amount > size) {
            currentPosition = size;
            print(size - amount, size, map);
        } else {
            if(currentPosition == 1) {
                currentPosition += amount;
            }
            if(currentPosition + amount > size) {
                currentPosition = size;
                print(size - amount, size, map);
                return currentPosition;
            }
            print(currentPosition, currentPosition + amount, map);
            currentPosition += amount;
        }
        return currentPosition;
    }


    private void print(int first, int second, Map<Integer, ContactPerson> map) {
        for(int i = first; i < second; i++) {
            System.out.println(i + "." + map.get(i).getFirstName() + " " + map.get(i).getLastName());
        }
    }

    public boolean checkContact(String accountNO) {
        for (ContactPerson contactPerson : contactPersonList) {
            if (contactPerson.getAccountNumber().equals(accountNO)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ContactPersonDB that = (ContactPersonDB) obj;
        return Objects.equals(contactPersonList, that.contactPersonList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contactPersonList);
    }
}
