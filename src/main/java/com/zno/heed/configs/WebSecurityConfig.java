package com.zno.heed.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
			.requestMatchers(HttpMethod.POST, "/update").authenticated()
			.requestMatchers(HttpMethod.POST, "/save").authenticated()
			.requestMatchers(HttpMethod.GET, "/view?*").authenticated()
			.anyRequest().permitAll()
			.and().httpBasic().and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}
}
