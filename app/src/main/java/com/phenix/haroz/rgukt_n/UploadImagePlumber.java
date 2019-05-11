package com.phenix.haroz.rgukt_n;

public class UploadImagePlumber {
    private String id;
    private String serviceType;
    private String url;
    private String plumComponent;
    private String blockname;
    private String floorno;
    private String roomno;
    private String date_Time;
    private String mail;

    public UploadImagePlumber() {
    }
    public UploadImagePlumber(String id, String serviceType, String url, String plumComponent, String blockname, String floorno, String roomno, String date_Time, String mail) {
        this.id = id;
        this.serviceType = serviceType;
        this.url = url;
        this.plumComponent = plumComponent;
        this.blockname = blockname;
        this.floorno = floorno;
        this.roomno = roomno;
        this.date_Time = date_Time;
        this.mail = mail;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlumComponent() {
        return plumComponent;
    }

    public void setPlumComponent(String plumComponent) {
        this.plumComponent = plumComponent;
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

    public String getDate_Time() {
        return date_Time;
    }

    public void setDate_Time(String date_Time) {
        this.date_Time = date_Time;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
