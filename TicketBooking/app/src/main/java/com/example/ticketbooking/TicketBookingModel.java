package com.example.ticketbooking;

import com.google.firebase.database.Exclude;

public class TicketBookingModel {
    private String id;
    private String source;
    private String destination;
    private String seatnum;
    private String date;
    private String age;
    private String amount;

    public TicketBookingModel() {
        //empty constructor needed
    }



    public TicketBookingModel(String id, String source, String destination, String seatnum, String date,String age,String amount) {

        this.id = id;
        this.source = source;
        this.seatnum = seatnum;
        this.destination = destination;
        this.date = date;
        this.age = age;
        this.amount = amount;

    }




    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeatnum() {
        return seatnum;
    }

    public void setSeatnum(String seatnum) {
        this.seatnum = seatnum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}


