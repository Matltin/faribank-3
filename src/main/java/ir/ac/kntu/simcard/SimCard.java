package ir.ac.kntu.simcard;

public class SimCard {
    private String fName;
    private String lName;
    private String phoneNumber;
    private long charge;

    public SimCard(String fName, String lName, String phoneNumber, long charge) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNumber = phoneNumber;
        this.charge = charge;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getCharge() {
        return charge;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return "SimCard{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", charge=" + charge +
                '}';
    }
}
