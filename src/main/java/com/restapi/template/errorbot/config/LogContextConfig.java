package com.restapi.template.errorbot.config;

import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.template.errorbot.ErrorLogsRepository;
import com.restapi.template.errorbot.ErrorReportAppender;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LogContextConfig implements InitializingBean {

    private final LogConfig logConfig;
    private final ErrorLogsRepository errorLogsRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void afterPropertiesSet() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        ErrorReportAppender errorReportAppender = new ErrorReportAppender(logConfig, errorLogsRepository, objectMapper);
        errorReportAppender.setContext(loggerContext);
        errorReportAppender.setName("customLogbackAppender");
        errorReportAppender.start();

        loggerContext.getLogger("ROOT").addAppender(errorReportAppender);
    }
}
