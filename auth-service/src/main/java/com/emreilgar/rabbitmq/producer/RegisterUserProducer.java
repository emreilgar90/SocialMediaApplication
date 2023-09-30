package com.emreilgar.rabbitmq.producer;

import com.emreilgar.rabbitmq.model.NewCreateUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange-auth")
    private String directExchange;

    @Value("${rabbitmq.bindingKeyRegister")
    private String registerBindingKey;

    public void sendNewUser(NewCreateUserModel model){
        rabbitTemplate.convertAndSend(directExchange,registerBindingKey,model);

    }
}
