package com.hugo.watchqueues.dto;

import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDTO {

    private String queueUrl;
    private String receiveMessageWaitTimeSeconds;

    public QueueDTO(String queueUrl, GetQueueAttributesResult attribute) {
        this.queueUrl = queueUrl;

        if (attribute.getAttributes().containsKey("ReceiveMessageWaitTimeSeconds")) {
            this.receiveMessageWaitTimeSeconds = attribute.getAttributes()
                    .get("ReceiveMessageWaitTimeSeconds");
        }
    }
}
