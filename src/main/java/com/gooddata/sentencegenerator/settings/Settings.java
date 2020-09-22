package com.gooddata.sentencegenerator.settings;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Settings {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
