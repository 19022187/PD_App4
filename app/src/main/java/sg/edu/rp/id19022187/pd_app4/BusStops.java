package sg.edu.rp.id19022187.pd_app4;

import java.io.Serializable;

public class BusStops implements Serializable {
    private String bsCode;
    private String rdName;
    private String desc;
    private Double lat;
    private Double lon;

    public BusStops(String bsCode, String rdName, String desc, Double lat, Double lon){
        this.bsCode = bsCode;
        this.rdName = rdName;
        this.desc = desc;
        this.lat = lat;
        this.lon = lon;
    }

    public String getBsCode() {
        return bsCode;
    }

    public void setBsCode(String bsCode) {
        this.bsCode = bsCode;
    }

    public String getRdName() {
        return rdName;
    }

    public void setRdName(String rdName) {
        this.rdName = rdName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
