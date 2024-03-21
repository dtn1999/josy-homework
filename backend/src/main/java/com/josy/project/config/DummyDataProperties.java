package com.josy.project.config;

import com.josy.project.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ConfigurationProperties(prefix = "dummy")
public class DummyDataProperties {
    private List<User> users;
}
