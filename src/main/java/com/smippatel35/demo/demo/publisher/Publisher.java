package com.smippatel35.demo.demo.publisher;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class Publisher {

    private Integer publisherId;

    @Size(min = 1, max = 50, message = "Publisher name must be between 1 to 50 characters")
    private String name;

    @Email(message = "Please Enter a valid Email Id")
    private String emailId;

    @Size(min = 10, max = 10, message = "Please Enter a 10 digit phone number")
    private String phoneNumber;

    public Publisher() {
    }

    public Publisher(Integer publisherId, String name, String emailId, String phoneNumber) {
        this.publisherId = publisherId;
        this.name = name;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
