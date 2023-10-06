package com.jeonsee.jeonsee.tasklet;

import com.jeonsee.jeonsee.dao.Exhibition;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateDBTasklet implements Tasklet {
    private final MongoTemplate mongoTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Exhibition> exhibitionList = (List<Exhibition>) (chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("exhibitionList"));

        if(exhibitionList == null) return RepeatStatus.FINISHED;

        exhibitionList.forEach(exhibition -> {
            exhibition.setUpdateDate(new Date());
            mongoTemplate.save(exhibition);
        });

        return RepeatStatus.FINISHED;
    }
}
