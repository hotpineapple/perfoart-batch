package com.jeonsee.jeonsee.dto.xml.detail;

import com.jeonsee.jeonsee.dto.xml.ComMsgHeader;
import com.jeonsee.jeonsee.dto.xml.list.PerformanceMsgBody;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class PerformanceDetailDisplay {

    @XmlElement(name = "comMsgHeader")
    private ComMsgHeader comMsgHeader;

    @XmlElement(name = "msgBody")
    private PerformanceDetailMsgBody msgBody;

    @XmlElement(name = "seq")
    private int seq;

    @Override
    public String toString() {
        return "PerformanceDetailDisplay{" +
                "comMsgHeader=" + comMsgHeader +
                ", msgBody=" + msgBody +
                ", seq=" + seq +
                '}';
    }
}
