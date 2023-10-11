package com.emreilgar.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.exchange-auth")
    private String exchange;

    /***********************************************************/

    @Value("${rabbitmq.queueRegister")//1
    private String queueNameRegister;
    @Value("${rabbitmq.bindingKeyRegister")//2
    private String bindingKeyRegister;
    /***********************************************************/
    @Value("${rabbitmq.queueEmail}")
    private String queueEmail;//1
    @Value("${rabbitmq.bindingKeyEmail}")
    private String emailBindingKey;//2
    /***********************************************************/
    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }
    /***********************************************************/


    @Bean
    Queue registerQueue(){  //yeni kuyruk oluşturacağında yenisini yazıyoruz.
        return new Queue(queueNameRegister);
    } //3-> Register kuyruğu
    @Bean
    Queue emailQueue(){  //yeni kuyruk oluşturacağında yenisini yazıyoruz.
        return new Queue(queueEmail);
    } //3->Email kuyruğu

    /***********************************************************/

    @Bean //4->Kuyruğu bağladık
    public Binding bindingRegister(final Queue registerQueue, final DirectExchange exchangeAuth ){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(bindingKeyRegister);
    }
    @Bean //4->Kuyruğu bağladık
    public Binding bindingEmail(final Queue emailQueue, final DirectExchange exchangeAuth ){
        return BindingBuilder.bind(emailQueue).to(exchangeAuth).with(emailBindingKey);
    }

}
