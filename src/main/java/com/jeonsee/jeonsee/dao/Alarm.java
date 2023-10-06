package com.jeonsee.jeonsee.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Getter
@Setter
@Document(collection = "alarm")
public class Alarm {
    @Id
    private BigInteger _id;
    private String userEmail;
    private String keyword;
    private boolean isEnabled;

    public Alarm() {}
    public Alarm(String userEmail, String keyword) {
        this.userEmail = userEmail;
        this.keyword = keyword;
        this.isEnabled = true;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

}