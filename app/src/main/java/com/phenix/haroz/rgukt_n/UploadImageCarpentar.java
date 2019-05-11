package com.phenix.haroz.rgukt_n;

public class UploadImageCarpentar {
    private String id;
    private String serviceType;
    private String Curl;
    private String carComponent;
    private String blockname;
    private String floorno;
    private String roomno;
    private String mail;
    private String date_Time;

    public UploadImageCarpentar() {
    }

    public UploadImageCarpentar(String id, String serviceType, String curl, String carComponent, String blockname, String floorno, String roomno, String mail, String date_Time) {
        this.id = id;
        this.serviceType = serviceType;
        Curl = curl;
        this.carComponent = carComponent;
        this.blockname = blockname;
        this.floorno = floorno;
        this.roomno = roomno;
        this.mail = mail;
        this.date_Time = date_Time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCurl() {
        return Curl;
    }

    public void setCurl(String curl) {
        Curl = curl;
    }

    public String getCarComponent() {
        return carComponent;
    }

    public void setCarComponent(String carComponent) {
        this.carComponent = carComponent;
    }

    public String getBlockname() {
        return blockname;
    }

    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }

    public String getFloorno() {
        return floorno;
    }

    public void setFloorno(String floorno) {
        this.floorno = floorno;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDate_Time() {
        return date_Time;
    }

    public void setDate_Time(String date_Time) {
        this.date_Time = date_Time;
    }
}
