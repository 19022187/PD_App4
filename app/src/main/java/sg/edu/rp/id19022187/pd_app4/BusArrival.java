package sg.edu.rp.id19022187.pd_app4;

public class BusArrival {

    private String busNum;
    private int mobileNumber;
    private String email;
    private int preferredMode;

    // TODO: Question 1 - Constructor


    public BusArrival(String busNum, int mobileNumber, String email, int preferredMode) {
        this.busNum = busNum;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.preferredMode = preferredMode;
    }

    public String getBusNum() {
        return busNum;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPreferredMode() {
        return preferredMode;
    }

    public void setPreferredMode(int preferredMode) {
        this.preferredMode = preferredMode;
    }
}