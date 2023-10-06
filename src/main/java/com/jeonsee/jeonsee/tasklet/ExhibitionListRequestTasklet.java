package com.jeonsee.jeonsee.tasklet;

import com.jeonsee.jeonsee.dao.Exhibition;
import com.jeonsee.jeonsee.dto.xml.list.Performance;
import com.jeonsee.jeonsee.dto.xml.list.PerformanceDisplay;
import com.jeonsee.jeonsee.util.HttpXmlParser;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ExhibitionListRequestTasklet implements Tasklet {
    @Value("${api.key}")
    private String key;
    private final String baseUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period?serviceKey=";
    private final int rowPerPage = 1000;
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = Logger.getLogger(ExhibitionListRequestTasklet.class.getName());

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            // 마지막 seq 조회
            Query query = new Query();
            query.with(Sort.by(Sort.Order.desc("seq")));
            Exhibition result = mongoTemplate.findOne(query, Exhibition.class);
            int lastSeq = result == null ? -1 : result.getSeq();

            // 전체 문서 개수로 페이지 계산
            PerformanceDisplay performanceDisplay1 = HttpXmlParser.parseList(getRequest(0));
            if (!performanceDisplay1.getComMsgHeader().isValid()) {
                throw new RuntimeException(performanceDisplay1.getComMsgHeader().getErrMsg());
            }

            int totalCnt = performanceDisplay1.getMsgBody().getTotalCount();
            int totalPage = totalCnt % rowPerPage == 0 ? totalCnt / rowPerPage : totalCnt / rowPerPage + 1;

            // api 요청 중 마지막 seq 과 같거나 작은 문서를 필터링
            List<Performance> performanceList = IntStream.rangeClosed(1, totalPage)
                .mapToObj((page) -> getRequest(page))
                .map(req -> {
                    PerformanceDisplay performanceDisplay2 = HttpXmlParser.parseList(req);

                    if(!performanceDisplay2.getComMsgHeader().isValid()) {
                        throw new RuntimeException(performanceDisplay2.getComMsgHeader().getErrMsg());
                    }

                    return performanceDisplay2.getMsgBody().getPerformanceList();
                })
                .filter(performances -> performances != null)
                .flatMap(performances -> performances.stream())
                .filter(performance -> performance.getSeq() > lastSeq)
                .collect(Collectors.toList());

            logger.info("result count: " + performanceList.size());
            ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            jobExecutionContext.put("performanceList", performanceList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RepeatStatus.FINISHED;
    }

    private Request getRequest(int page) {
        String startDate = "20200101";
        String endDate = "21001231";
        String uri = baseUrl + key + "&from=" + startDate + "&to=" + endDate + "&cPage=" + page + "&rows=" + rowPerPage;

        return new Request.Builder().get().url(uri).build();
    }
}
