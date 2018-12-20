package sercandevops.com.wifimanagerapp;

import java.util.ArrayList;

public class WifiBilgi {


    private String adi;
    private String mac;
    private String sinyal;

    public WifiBilgi(String adi, String mac,String sinyal) {
        this.adi = adi;
        this.mac = mac;
        this.sinyal = sinyal;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSinyal() {
        return sinyal;
    }

    public void setSinyal(String sinyal) {
        this.sinyal = sinyal;
    }
}
