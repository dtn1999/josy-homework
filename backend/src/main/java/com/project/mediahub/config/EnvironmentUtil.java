package com.project.mediahub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@RequiredArgsConstructor
public class EnvironmentUtil {
    private final Environment environment;

    private String port;
    private String hostname;

    public String getPort() {
        if (port == null) {
            port = environment.getProperty("local.server.port");
        }
        return port;
    }

    public Integer getPortAsInt() {
        return Integer.valueOf(getPort());
    }

    public String getHostname() throws UnknownHostException {
        if (hostname == null) {
            // just because this app will never be deployed to production
            hostname = "localhost";
        }
        return hostname;
    }

    public String getServerUrl() throws UnknownHostException {
        return "http://" + getHostname() + ":" + getPort();
    }
}
