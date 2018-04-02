package com.hugo.watchqueues.controller;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.hugo.watchqueues.dto.QueueDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class QueueController {

    private static final String TWENTY_SECONDS = "20";

    AmazonSQS amazonSQS;

    public QueueController(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    @GetMapping(value="/queue")
    public ResponseEntity getQueues() {

        List<QueueDTO> queues = new ArrayList<QueueDTO>();
        List<String> attributes = new ArrayList<>();
        attributes.add("ReceiveMessageWaitTimeSeconds");

        try {
            ListQueuesResult queuesResult = amazonSQS.listQueues();

            List<String> queuesUrl = queuesResult.getQueueUrls();

            for (String url : queuesUrl) {

                QueueDTO queue = new QueueDTO(url, amazonSQS.getQueueAttributes(url, attributes));

                if (!TWENTY_SECONDS.equalsIgnoreCase(queue.getReceiveMessageWaitTimeSeconds())) {
                    queues.add(queue);
                }
                System.out.println("Fila verificada: " + queue);
            }

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while search the queues.");
        }

        return ResponseEntity.ok(queues);
    }

}
