package com.jeonsee.jeonsee.tasklet;

import com.jeonsee.jeonsee.dao.Exhibition;
import com.jeonsee.jeonsee.dto.xml.detail.PerformanceDetail;
import com.jeonsee.jeonsee.dto.xml.detail.PerformanceDetailDisplay;
import com.jeonsee.jeonsee.dto.xml.list.Performance;
import com.jeonsee.jeonsee.util.HttpXmlParser;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExhibitionDetailRequestTasklet implements Tasklet {
    @Value("${api.key}")
    private String key;
    private final String baseUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?serviceKey=";
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
	    try {
            List<Performance> performanceList = (List<Performance>) (chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("performanceList"));

            if(performanceList == null || performanceList.size() == 0) return RepeatStatus.FINISHED;

            List<Exhibition> exhibitionList = new ArrayList<>();
            performanceList.forEach(
                performance -> {
                    PerformanceDetailDisplay performanceDetailDisplay = HttpXmlParser.parseDetail(getRequest(performance.getSeq()));

                    if (!performanceDetailDisplay.getComMsgHeader().isValid()) {
                        throw new RuntimeException(performanceDetailDisplay.getComMsgHeader().getErrMsg());
                    }

                    PerformanceDetail performanceDetail = performanceDetailDisplay.getMsgBody().getPerformanceDetail();
                    Exhibition exhibition = new Exhibition();
                    exhibition.setBasicInfo(performance);
                    exhibition.setDetailInfo(performanceDetail);

                    exhibitionList.add(exhibition);
		    
                });

            ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            jobExecutionContext.put("exhibitionList", exhibitionList);
        } catch (Exception e) {
	       	e.printStackTrace();
        }
        return RepeatStatus.FINISHED;
    }

    private Request getRequest(int seq) {
        String uri = baseUrl + key + "&seq=" + seq;

        return new Request.Builder().get().url(uri).build();
    }
}
