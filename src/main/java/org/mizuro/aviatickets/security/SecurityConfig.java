package org.mizuro.aviatickets.security;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private UserDetailsServiceImpl userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try {
            http.authorizeHttpRequests(requests ->
                            requests
                                    .requestMatchers("/css/**", "/fonts/**", "/js/**", "/images/**").permitAll()
                                    .requestMatchers("/", "/registration").permitAll()
                                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                    .requestMatchers(HttpMethod.PATCH, "/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/api/races", "/api/racesList", "/races/addFoundedRaces").permitAll() // разрешаем доступ к обоим путям
                                    .anyRequest().authenticated()
                    )
                    .formLogin(form ->
                            form.loginPage("/login")
                                    .defaultSuccessUrl("/races/selectionMenu", true)
                                    .permitAll()
                    )
                    .logout(LogoutConfigurer::permitAll)
                    .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/races/addFoundedRaces"));

            return http.build();
        } catch (Exception e) {
            logger.error("An error occurred while configuring security.", e);
            throw e;
        }
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
