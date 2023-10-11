package com.emreilgar.rabbitmq.consumer;

import com.emreilgar.rabbitmq.model.EmailSenderModel;
import com.emreilgar.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {
    private final EmailSenderService emailSenderService;

    @RabbitListener(queues="${rabbitmq.bindingKeyEmail}") //-> burayı dinlemesi için
    public void activationCode(EmailSenderModel model){
        log.info("Model {}",model.toString());
        emailSenderService.sendMail(model);

    }
}
