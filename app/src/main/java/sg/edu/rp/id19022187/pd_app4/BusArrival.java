package sg.edu.rp.id19022187.pd_app4;

public class BusArrival {

    private String busNum;
    private String time1;
    private String time2;
    private String time3;

    // TODO: Question 1 - Constructor


    public BusArrival(String busNum, String time1, String time2, String time3) {
        this.busNum = busNum;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
    }

    public String getBusNum() {
        return busNum;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String email) { this.time2 = time2; }

    public String getTime3() { return time3; }

    public void setTime3(String time3) { this.time3 = time3; }
}