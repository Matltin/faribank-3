package ir.ac.kntu.phone;

import ir.ac.kntu.db.SimCardDB;

import java.util.Objects;

public class Phone {
    private String phoneNumber;
    private SimCardDB simCardDB;
    private long chargeCredit;

    public Phone(String phoneNumber, SimCardDB simCardDB) {
        this.phoneNumber = phoneNumber;
        this.simCardDB = simCardDB;
        checkExistPhone(phoneNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getChargeCredit() {
        return chargeCredit;
    }

    public void setChargeCredit(long chargeCredit) {
        this.chargeCredit = chargeCredit;
    }

    public void increaseChargeCredit(long chargeCredit) {
        setChargeCredit(getChargeCredit() + chargeCredit);
    }

    private void checkExistPhone(String phoneNumber) {
        for(Phone phone : simCardDB.getPhones()) {
            if(phone.getPhoneNumber().equals(phoneNumber)) {
                chargeCredit = phone.getChargeCredit();
                return;
            }
        }
        chargeCredit = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Phone phone = (Phone) obj;
        return Objects.equals(phoneNumber, phone.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phoneNumber);
    }
}
