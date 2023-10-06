package com.jeonsee.jeonsee.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.jeonsee.jeonsee.dto.xml.detail.PerformanceDetail;
import com.jeonsee.jeonsee.dto.xml.list.Performance;
import lombok.Getter;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Document(collection = "exhibition")
public class Exhibition {
    @Id
    private BigInteger _id;
    // 목록 조회
    private int seq;
    private String title;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;
    private String place;
    private String realmName;
    private String area;
    private String thumbnail;
    private float gpsX;
    private float gpsY;
    // 상세 조회
    private String subTitle;
    private String content1;
    private String content2;
    private String price;
    private String url;
    private String phone;
    private String imgUrl;
    private String placeUrl;
    private String placeAddr;
    private String placeSeq;
    // 추가
    private Date updateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exhibition that = (Exhibition) o;
        return seq == that.seq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    public void setBasicInfo(Performance performance) {
        this.seq = performance.getSeq();
        this.title = StringEscapeUtils.unescapeHtml4(performance.getTitle());
        this.startDate = performance.getStartDate();
        this.endDate = performance.getEndDate();
        this.place = performance.getPlace();
        this.realmName = performance.getRealmName();
        this.area = performance.getArea();
        this.gpsX = performance.getGpsX();
        this.gpsY = performance.getGpsY();
        this.thumbnail = performance.getThumbnail();
    }
    public void setDetailInfo(PerformanceDetail performanceDetail) {
        this.subTitle = performanceDetail.getSubTitle();
        this.content1 = performanceDetail.getContents1();
        this.content2 = performanceDetail.getContents2();
        this.price = performanceDetail.getPrice();
        this.url = performanceDetail.getUrl();
        this.phone = performanceDetail.getPhone();
        this.imgUrl = performanceDetail.getImgUrl();
        this.placeUrl = performanceDetail.getPlaceUrl();
        this.placeAddr = performanceDetail.getPlaceAddr();
        this.placeSeq = performanceDetail.getPlaceSeq();
    }

    public void setUpdateDate(Date date) { this.updateDate = date; }

    @Override
    public String toString() {
        return "Exhibition{" +
                "_id=" + _id +
                ", seq=" + seq +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", place='" + place + '\'' +
                ", realmName='" + realmName + '\'' +
                ", area='" + area + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", gpsX=" + gpsX +
                ", gpsY=" + gpsY +
                ", subTitle='" + subTitle + '\'' +
                ", content1='" + content1 + '\'' +
                ", content2='" + content2 + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", phone='" + phone + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", placeUrl='" + placeUrl + '\'' +
                ", placeAddr='" + placeAddr + '\'' +
                ", placeSeq='" + placeSeq + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }
}
