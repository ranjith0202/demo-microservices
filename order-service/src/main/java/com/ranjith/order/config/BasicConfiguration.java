package com.ranjith.order.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfiguration {
	
	@Bean
	public ModelMapper modelMapper(){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
        .setSkipNullEnabled(true);        // 🚀 Prevent null overriding

        return mapper;
	}
}
