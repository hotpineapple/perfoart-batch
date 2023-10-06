package com.jeonsee.jeonsee.util;

import com.jeonsee.jeonsee.dto.xml.detail.PerformanceDetailDisplay;
import com.jeonsee.jeonsee.dto.xml.list.PerformanceDisplay;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.regex.Pattern;

public class HttpXmlParser {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final Pattern pattern = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+");

    public static PerformanceDisplay parseList(Request request) {
        Response response = null;

        try {
            response = CLIENT.newCall(request).execute();
            String xmlString = pattern.matcher(response.body().string()).replaceAll("");

            JAXBContext jaxbContext = JAXBContext.newInstance(PerformanceDisplay.class);
            Unmarshaller unmarshal = jaxbContext.createUnmarshaller();

            return (PerformanceDisplay) unmarshal.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public static PerformanceDetailDisplay parseDetail(Request request) {
        Response response = null;

        try {
            response = CLIENT.newCall(request).execute();

            String xmlString = pattern.matcher(response.body().string()).replaceAll("");

            JAXBContext jaxbContext = JAXBContext.newInstance(PerformanceDetailDisplay.class);
            Unmarshaller unmarshal = jaxbContext.createUnmarshaller();

            return (PerformanceDetailDisplay) unmarshal.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
