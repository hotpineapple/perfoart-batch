package com.jeonsee.jeonsee.dto.xml.detail;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.jeonsee.jeonsee.dao.Exhibition;
import com.jeonsee.jeonsee.util.LocalDateAdapter;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "msgBody")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class PerformanceDetailMsgBody {
    @XmlElement(name = "seq")
    private int seq;

    @XmlElement(name = "perforInfo")
    private PerformanceDetail performanceDetail;

    @Override
    public String toString() {
        return "PerformanceDetailMsgBody{" +
                "seq=" + seq +
                ", performanceDetail=" + performanceDetail +
                '}';
    }
}
