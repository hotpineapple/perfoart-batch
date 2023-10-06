package com.jeonsee.jeonsee.dto.xml.list;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.jeonsee.jeonsee.util.LocalDateAdapter;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@XmlRootElement(name = "perforList")
@XmlAccessorType(XmlAccessType.FIELD)
public class Performance {
    @XmlElement(name = "seq")
    private int seq;

    @XmlElement(name = "title")
    private String title;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "startDate")
    private LocalDate startDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "endDate")
    private LocalDate endDate;

    @XmlElement(name = "place")
    private String place;

    @XmlElement(name = "realmName")
    private String realmName;

    @XmlElement(name = "area")
    private String area;

    @XmlElement(name = "thumbnail")
    private String thumbnail;

    @XmlElement(name = "gpsX")
    private float gpsX;

    @XmlElement(name = "gpsY")
    private float gpsY;

    private Date updateDate;

    public void setUpdateDate(Date date) { this.updateDate = date; }

    @Override
    public String toString() {
        return "Performance{" +
                "seq=" + seq +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", place='" + place + '\'' +
                ", realmName='" + realmName + '\'' +
                ", area='" + area + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", gpsX=" + gpsX +
                ", gpsY=" + gpsY +
                ", updateDate=" + updateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Performance that = (Performance) o;
        return seq == that.seq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
