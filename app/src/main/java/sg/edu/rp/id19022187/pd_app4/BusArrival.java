package sg.edu.rp.id19022187.pd_app4;

public class BusArrival {

    private String busNum;
    private String time;
    private String email;
    private int preferredMode;

    // TODO: Question 1 - Constructor


    public BusArrival(String busNum, String time, String email, int preferredMode) {
        this.busNum = busNum;
        this.time = time;
        this.email = email;
        this.preferredMode = preferredMode;
    }

    public String getBusNum() {
        return busNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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