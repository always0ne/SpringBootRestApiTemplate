package com.restapi.template.errorbot;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.template.errorbot.config.LogConfig;
import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackAttachment;
import net.gpedro.integrations.slack.SlackField;
import net.gpedro.integrations.slack.SlackMessage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class ErrorReportAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private final LogConfig logConfig;
    private final ErrorLogsRepository errorLogsRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (eventObject.getLevel().isGreaterOrEqual(logConfig.getLevel())) {
            ErrorLogs errorLog = new ErrorLogs(eventObject);
            if (logConfig.getDatabase().isEnabled())
                errorLogsRepository.save(errorLog);
            if (logConfig.getSlack().isEnabled())
                sendSlackMessage(errorLog);
        }
    }

    private void sendSlackMessage(ErrorLogs errorLog) {
        errorLog.markAsAlert();
        SlackApi slackApi = new SlackApi(logConfig.getSlack().getWebHookUrl());

        SlackMessage slackMessage = new SlackMessage("");
        slackMessage.setChannel("#" + logConfig.getSlack().getChannel());
        slackMessage.setUsername(logConfig.getSlack().getUserName());
        slackMessage.setIcon(":exclamation:");

        SlackAttachment slackAttachment = new SlackAttachment();
        slackAttachment.setFallback("Api 서버 에러발생!! 확인요망");
        slackAttachment.setColor("danger");
        if (logConfig.getDatabase().isEnabled())
            slackAttachment.setTitle(errorLog.getErrorInfo().getMessage() + "[" + errorLog.getId() + "] ");
        else
            slackAttachment.setTitle(errorLog.getErrorInfo().getMessage());
        slackAttachment.setText(errorLog.getErrorInfo().getTrace());

        List<SlackField> fields = new ArrayList<>();

        fields.add(generateSlackField(
                "Error Message",
                errorLog.getErrorInfo().getMessage(),
                false));

        fields.add(generateSlackField(
                "Request URL",
                errorLog.getRequestInfo().getPath(),
                true));

        fields.add(generateSlackField(
                "Request Parameter",
                toPrettyJson(errorLog.getRequestInfo().getParameterMap()),
                true));

        fields.add(generateSlackField(
                "Request Header",
                toPrettyJson(errorLog.getRequestInfo().getHeaderMap()),
                false));

        fields.add(generateSlackField(
                "Request Body",
                errorLog.getRequestInfo().getBody(),
                false));

        fields.add(generateSlackField(
                "User Info",
                errorLog.getUserInfo(),
                true));

        fields.add(generateSlackField(
                "User Agent",
                errorLog.getRequestInfo().getAgentDetail(),
                true));

        fields.add(generateSlackField(
                "Time",
                errorLog.getErrorDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                true));

        fields.add(generateSlackField(
                "Server Name",
                errorLog.getSystemInfo().getServerName(),
                true));

        fields.add(generateSlackField(
                "Server System OS",
                errorLog.getSystemInfo().getSystem(),
                true));

        fields.add(generateSlackField(
                "Server Host Name",
                errorLog.getSystemInfo().getHostName(),
                true));

        slackAttachment.setFields(fields);
        slackMessage.setAttachments(Collections.singletonList(slackAttachment));
        slackApi.call(slackMessage);
    }

    private SlackField generateSlackField(String title, String value, Boolean shorten) {
        SlackField slackField = new SlackField();
        slackField.setTitle(title);
        slackField.setValue(value);
        slackField.setShorten(shorten);
        return slackField;
    }

    private String toPrettyJson(String rawJson) {
        try {
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(objectMapper.readValue(rawJson, Object.class));
        } catch (Exception e) {
            return "";
        }
    }
}