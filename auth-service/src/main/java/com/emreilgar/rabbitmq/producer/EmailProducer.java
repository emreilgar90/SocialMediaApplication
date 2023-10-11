package com.emreilgar.rabbitmq.producer;

import com.emreilgar.rabbitmq.model.EmailSenderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange-auth")
    private String directExchange;

    @Value("${rabbitmq.bindingKeyRegister")
    private String emailBindingKey;

    public void sendActivationCode(EmailSenderModel model){
        rabbitTemplate.convertAndSend(directExchange,emailBindingKey,model);

    }
}
