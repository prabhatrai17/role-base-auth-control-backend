package com.virtusa.rbac.serviceImpl;

import com.virtusa.rbac.dto.ForgotPasswordEmailDto;
import com.virtusa.rbac.repository.ForgotPasswordEmailRepository;
import com.virtusa.rbac.service.ForgotPasswordEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements ForgotPasswordEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ForgotPasswordEmailRepository emailRepository;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendForgotPasswordEmail(ForgotPasswordEmailDto emailDto) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(sender);
        mail.setTo(emailDto.recipient());
        mail.setSubject(emailDto.subject());
        mail.setText(emailDto.body());

        javaMailSender.send(mail);
        return "Mail sent successfully";
    }
}
