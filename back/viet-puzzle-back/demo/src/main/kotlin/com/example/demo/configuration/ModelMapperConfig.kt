package com.example.demo.configuration

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelMapperConfig {

    @Bean
    fun modelmapper(): ModelMapper {
        return ModelMapper()
    }
}