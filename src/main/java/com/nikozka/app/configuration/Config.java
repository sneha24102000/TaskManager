package com.nikozka.app.configuration;

import com.nikozka.app.utils.TaskStatusValidatorFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public TaskStatusValidatorFactory taskStatusUpdaterFactory() {
        return new TaskStatusValidatorFactory();
    }

}
