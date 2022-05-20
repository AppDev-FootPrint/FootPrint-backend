package com.footprint.config;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.auth.filter.JwtAuthenticationFilter;
import com.footprint.auth.filter.RestfulLoginFilter;
import com.footprint.auth.handler.RestfulAuthenticationSuccessHandler;
import com.footprint.auth.jwt.JwtService;
import com.footprint.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * Created by ShinD on 2022/05/14.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	private final ObjectMapper objectMapper;
	private final JwtService jwtService;
	private final MemberRepository memberRepository;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin().disable()
			.httpBasic().disable() // Header 에 username, password 를 실어 보내 인증하는 방식 비활성화
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(STATELESS)

			.and()
			.authorizeRequests()
			.antMatchers("/login", "/signUp").permitAll()//TODO 따로 빼서 관리하는 것이 나을지, 아니면 이대로 사용할지
			.anyRequest().authenticated();

		http.addFilterAfter(restfulLoginFilter(), LogoutFilter.class);
		http.addFilterBefore(jwtAuthenticationFilter(), RestfulLoginFilter.class);
	}



	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();//bcrypt 사용
	}


	@Bean
	public UsernamePasswordAuthenticationFilter restfulLoginFilter() throws Exception {
		RestfulLoginFilter restfulLoginFilter = new RestfulLoginFilter(authenticationManager(), objectMapper);

		restfulLoginFilter.setAuthenticationSuccessHandler(restfulAuthenticationSuccessHandler());
		return restfulLoginFilter;
	}

	@Bean
	public AuthenticationSuccessHandler restfulAuthenticationSuccessHandler() {
		return new RestfulAuthenticationSuccessHandler(jwtService);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtService, memberRepository);
	}
}
