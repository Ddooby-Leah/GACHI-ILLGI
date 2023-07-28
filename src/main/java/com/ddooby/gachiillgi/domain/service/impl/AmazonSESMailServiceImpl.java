package com.ddooby.gachiillgi.domain.service.impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import com.ddooby.gachiillgi.domain.service.MailService;
import com.ddooby.gachiillgi.interfaces.dto.response.MailServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AmazonSESMailServiceImpl implements MailService {
    @Value("${aws.ses.from}")
    private String from;
    private final UserRepository userRepository;
    private final TemplateEngine htmlTemplateEngine;
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public MailServiceResponseDTO send(String subject, Map<String, Object> variables, String to) {

        User user = userRepository.findByEmail(to)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        if (user.isActivatedUser()) {
            throw new BizException(UserErrorCodeEnum.USER_NOT_PENDING_STATUS);
        }

        String content = htmlTemplateEngine.process("index", createContext(variables));
        SendEmailRequest sendEmailRequest = createSendEmailRequest(subject, content, to);
        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(sendEmailRequest);

        return MailServiceResponseDTO.builder()
                .messageId(sendEmailResult.getMessageId())
                .build();
    }

    private Context createContext(Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);

        return context;
    }

    private SendEmailRequest createSendEmailRequest(String subject, String content, String... to) {
        return new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withSource(from)
                .withMessage(new Message()
                        .withSubject(new Content().withCharset(StandardCharsets.UTF_8.name()).withData(subject))
                        .withBody(new Body().withHtml(new Content().withCharset(StandardCharsets.UTF_8.name()).withData(content)))
                );
    }
}
