package com.emreilgar.rabbitmq.consumer;

import com.emreilgar.mapper.IUserMapper;
import com.emreilgar.rabbitmq.model.NewCreateUserModel;
import com.emreilgar.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewUserConsumer {
    private final UserProfileService userProfileService;
    @RabbitListener(queues= ("${rabbitmq.queueRegister")) //yml da belirttiğim adresi dinleyecek sürekli
    public void newUserRegister(NewCreateUserModel model){
        log.info("User:{}",model.toString());
        userProfileService.save(IUserMapper.INSTANCE.toUserProfile(model));

    }
}
