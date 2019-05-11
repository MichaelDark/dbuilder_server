package com.mtemnohud.dbuilder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtemnohud.dbuilder.component.Validator;
import com.mtemnohud.dbuilder.repository.AuthoritiesRepository;
import com.mtemnohud.dbuilder.security.jwt.JwtSettings;
import com.mtemnohud.dbuilder.security.jwt.JwtTokenBuilder;
import com.mtemnohud.dbuilder.security.jwt.TokenAuthenticationProvider;
import com.mtemnohud.dbuilder.security.web.*;
import com.mtemnohud.dbuilder.service.impl.secured.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/**";
    private static final String LOGIN_ENTRY_POINT = "/login";
    private static final String[] SWAGGER_RESOURCES = {"/v2/api-docs*", "/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/swagger-resources/**"};

    private final DataSource dataSource;

    private final UserService userService;

    private final AuthoritiesRepository authoritiesRepository;

    private final Validator validator;

    @Value("${application.api.version.prefix}")
    private String apiPrefix;

    @Autowired
    public SecurityConfig(DataSource dataSource, UserService userService, AuthoritiesRepository authoritiesRepository, Validator validator) {
        this.dataSource = dataSource;
        this.userService = userService;
        this.authoritiesRepository = authoritiesRepository;
        this.validator = validator;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.trace("[SecurityConfig] [configure auth providers]");
        auth.authenticationProvider(tokenAuthenticationProvider());
        auth.jdbcAuthentication().dataSource(dataSource).authoritiesByUsernameQuery("select user_id, authority from authorities where user_id = (select user_id from users where username = ?)").passwordEncoder(passwordEncryptor());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_RESOURCES);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.trace("[SecurityConfig] [configure]");
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPoint()).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/url/*").permitAll()
                .antMatchers(HttpMethod.POST, "/user/registration").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/password").permitAll()
                .antMatchers(HttpMethod.POST, "/measurement").permitAll()
                .antMatchers(HttpMethod.GET, "/measurement/template").permitAll()
                .antMatchers(HttpMethod.POST, "/dbuilder").permitAll()
                .antMatchers(HttpMethod.POST, "/dbuilder/api").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(loginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(restTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private TokenAuthenticationFilter restTokenAuthenticationFilter() throws Exception {
        PathRequestMatcher matcher = new PathRequestMatcher(TOKEN_BASED_AUTH_ENTRY_POINT,
                Arrays.asList("/user/registration", "/user/password", "/measurement", "/measurement/template", "/dbuilder", "/dbuilder/api"));
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter(matcher, webAuthenticationUnsuccessfulHandler());
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public TokenAuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(jwtTokenBuilder());
    }

    @Bean
    public WebAuthenticationUnsuccessfulHandler webAuthenticationUnsuccessfulHandler() {
        return new WebAuthenticationUnsuccessfulHandler(objectMapper());
    }

    private LoginProcessingFilter loginProcessingFilter() throws Exception {
        LoginProcessingFilter filter = new LoginProcessingFilter(LOGIN_ENTRY_POINT, objectMapper(), jwtTokenBuilder(),
                webAuthenticationUnsuccessfulHandler(), userService, authoritiesRepository, validator);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public AuthenticationEntryPoint authEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public JwtTokenBuilder jwtTokenBuilder() {
        return new JwtTokenBuilder(jwtSettings());
    }

    @Bean
    @ConfigurationProperties(prefix = "application.security.jwt")
    public JwtSettings jwtSettings() {
        return new JwtSettings();
    }

    @Bean
    @ConfigurationProperties(prefix = "application.security.password")
    public PasswordMd5Encoder passwordEncryptor() {
        return new PasswordMd5Encoder();
    }

    private ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
