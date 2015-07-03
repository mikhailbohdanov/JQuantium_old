package com.quantium.web.test;


import java.util.Date;

public class MessageFromServer {
    private String context;
    private Date now ;
    private String date;


    public MessageFromServer() {
    }

    public MessageFromServer(String context) {
        this.context = context;
        now = new Date();
        date = now.toString();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public String getDate() {
        now = new Date();
        date = now.toString();
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MessageFromServer{" +
                "context='" + context + '\'' +
                ", now=" + now +
                ", date='" + date + '\'' +
                '}';
    }
}
