package com.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Locale;

public class AppointmentModel {

    private Integer id;
    private Integer customerId;
    private String title;
    private String description;
    private String location;
    private Integer contactId;
    private String type;
//    private Date date;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Integer userId;



    public AppointmentModel(Integer id, String title, String description, String location, Integer contactId, String type, ZonedDateTime startDate, ZonedDateTime endDate, Integer customerId, Integer userId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
//        this.date = date;
        this.startTime = startDate;
        this.endTime = endDate;
        this.customerId = customerId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contact) {
        this.contactId = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(ZonedDateTime startDate) {
        this.startTime = startDate;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endDate) {
        this.endTime = endDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
}
