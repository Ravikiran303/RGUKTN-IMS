package com.phenix.haroz.rgukt_n;

public class UploadImageElect {
    private String id;
    private String serviceType;
    private String Eurl;
    private String eleComponent;
    private String blockname;
    private String floorno;
    private String roomno;
    private String mail;
    private String date_Time;

    public UploadImageElect() {
    }
    public UploadImageElect(String id, String serviceType, String eurl, String eleComponent, String blockname, String floorno, String roomno, String mail, String date_Time) {
        this.id = id;
        this.serviceType = serviceType;
        Eurl = eurl;
        this.eleComponent = eleComponent;
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

    public String getEurl() {
        return Eurl;
    }

    public void setEurl(String eurl) {
        Eurl = eurl;
    }

    public String getEleComponent() {
        return eleComponent;
    }

    public void setEleComponent(String eleComponent) {
        this.eleComponent = eleComponent;
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
