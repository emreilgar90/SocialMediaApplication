package com.emreilgar.service;

import com.emreilgar.rabbitmq.model.EmailSenderModel;
import lombok.RequiredArgsConstructor;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor

public class EmailSenderService {

    private final JavaMailSender javaMailSender;


    public void sendMail(EmailSenderModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("dev.emre.ilgar");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Activation Code: ");
        mailMessage.setText(model.getActivationCode());
        javaMailSender.send(mailMessage);

    }
}
