package com.hy.gaeaadmin;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GaeaAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaeaAdminApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.LOOSE)
//                .setAmbiguityIgnored(true);
//        modelMapper.getConfiguration()
//                .setFieldMatchingEnabled(true).setFieldAccessLevel(AccessLevel.PRIVATE).setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
        return modelMapper;
    }
}
