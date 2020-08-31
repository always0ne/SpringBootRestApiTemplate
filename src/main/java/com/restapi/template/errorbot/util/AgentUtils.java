package com.restapi.template.errorbot.util;

import eu.bitwalker.useragentutils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AgentUtils {
    public static String getUserAgentString(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static UserAgent getUserAgent(HttpServletRequest request) {
        try {
            String userAgentString = getUserAgentString(request);
            return UserAgent.parseUserAgentString(userAgentString);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static OperatingSystem getUserOs(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        return userAgent == null ? OperatingSystem.UNKNOWN : userAgent.getOperatingSystem();
    }

    public static Browser getBrowser(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (userAgent == null || userAgent.getBrowser() == null)
            return Browser.UNKNOWN;
        return userAgent.getBrowser();
    }

    public static Version getBrowserVersion(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (userAgent == null || userAgent.getBrowserVersion() == null)
            return new Version("0", "0", "0");
        else
            return userAgent.getBrowserVersion();
    }

    public static BrowserType getBrowserType(HttpServletRequest request) {
        Browser browser = getBrowser(request);
        return browser == Browser.UNKNOWN ? BrowserType.UNKNOWN : browser.getBrowserType();
    }

    public static RenderingEngine getRenderingEngine(HttpServletRequest request) {
        Browser browser = getBrowser(request);
        return browser == Browser.UNKNOWN ? RenderingEngine.OTHER : browser.getRenderingEngine();
    }

    public static DeviceType getDeviceType(HttpServletRequest request) {
        OperatingSystem operatingSystem = getUserOs(request);
        return operatingSystem == null ? DeviceType.UNKNOWN : operatingSystem.getDeviceType();
    }

    public static Manufacturer getManufacturer(HttpServletRequest request) {
        OperatingSystem operatingSystem = getUserOs(request);
        return operatingSystem == null ? Manufacturer.OTHER : operatingSystem.getManufacturer();
    }

    public static Map<String, String> getAgentDetail(HttpServletRequest request) {
        Map<String, String> agentDetail = new HashMap<>();
        agentDetail.put("browser", getBrowser(request).toString());
        agentDetail.put("browserType", getBrowserType(request).toString());
        agentDetail.put("browserVersion", getBrowserVersion(request).toString());
        agentDetail.put("renderingEngine", getRenderingEngine(request).toString());
        agentDetail.put("os", getUserOs(request).toString());
        agentDetail.put("deviceType", getDeviceType(request).toString());
        agentDetail.put("manufacturer", getManufacturer(request).toString());

        return agentDetail;
    }
}
