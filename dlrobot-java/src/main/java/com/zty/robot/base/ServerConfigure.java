package com.zty.robot.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "robotserver")
public class ServerConfigure {

    private String url;
    private Long platformConnType;
    private String robotHashCode;
    private String userId;
    private Integer timeout;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPlatformConnType() {
        return platformConnType;
    }

    public void setPlatformConnType(Long platformConnType) {
        this.platformConnType = platformConnType;
    }

    public String getRobotHashCode() {
        return robotHashCode;
    }

    public void setRobotHashCode(String robotHashCode) {
        this.robotHashCode = robotHashCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
