package com.jeonsee.jeonsee.dto.xml.list;

import com.jeonsee.jeonsee.dto.xml.ComMsgHeader;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class PerformanceDisplay {

    @XmlElement(name = "comMsgHeader")
    private ComMsgHeader comMsgHeader;

    @XmlElement(name = "msgBody")
    private PerformanceMsgBody msgBody;
}
