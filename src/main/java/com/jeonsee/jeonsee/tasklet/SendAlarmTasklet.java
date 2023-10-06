package com.jeonsee.jeonsee.tasklet;

import com.jeonsee.jeonsee.dao.Alarm;
import com.jeonsee.jeonsee.dao.Exhibition;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class SendAlarmTasklet implements Tasklet {
    private final MongoTemplate mongoTemplate;
    private final JavaMailSenderImpl mailSender;
    private static final Logger logger = Logger.getLogger(ExhibitionListRequestTasklet.class.getName());

    @Value("${spring.mail.username}")
    private String username;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("isEnabled").is(true));
        List<Alarm> alarmList = mongoTemplate.find(query, Alarm.class, "alarm");

        List<Exhibition> newExhibitionList = (List<Exhibition>) (chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("exhibitionList"));

        int cnt = 0;
        for (Alarm alarm : alarmList) {
            for (Exhibition exhibition : newExhibitionList) {
                if (exhibition.getTitle().contains(alarm.getKeyword())) {
                    cnt++;
                    SimpleMailMessage smm = new SimpleMailMessage();
                    smm.setFrom(String.valueOf(new InternetAddress(username, "퍼포앤아트")));
                    smm.setTo(alarm.getUserEmail());
                    smm.setSubject(exhibition.getTitle() + "공연전시가 새로 등록되었습니다");
                    smm.setText("퍼포 앤 아트에서 확인해보세요!");

                    mailSender.send(smm);
                }
            }
        }
        logger.info("sent count: " + cnt);

        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        jobExecutionContext.put("info", newExhibitionList);

        return RepeatStatus.FINISHED;
    }
}
