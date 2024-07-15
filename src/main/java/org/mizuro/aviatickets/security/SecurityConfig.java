package org.mizuro.aviatickets.security;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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
        LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/auth/login");
        try {
//            http.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
//            http.oauth2Login(Customizer.withDefaults());
//            return http.authorizeHttpRequests(request -> request
//                    .anyRequest().authenticated()).build();
            http.authorizeHttpRequests(requests ->
                            requests
                                    .requestMatchers("/css/**", "/fonts/**", "/js/**", "/images/**").permitAll()
                                    .requestMatchers("/", "/auth/**").permitAll()
                                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                    .requestMatchers("GET", "POST", "PUT", "DELETE", "PATCH", "/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/api/races", "/api/racesList", "/races/addFoundedRaces").permitAll()
                                    .anyRequest().authenticated()
                    )
                    .formLogin(login -> login
                            .loginPage("/auth/login").permitAll().defaultSuccessUrl("/races/selectionMenu"))
                    .logout(LogoutConfigurer::permitAll)
                    .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/races/addFoundedRaces"))
                    .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                    .authenticationEntryPoint(entryPoint)
                    .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/error"))
            );
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

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
//            var roles = (List<String>) jwt.getClaimAsStringList("realm_access");
//
//            return Stream.concat(authorities.stream(),
//                            roles.stream().filter(role -> role.startsWith("ROLE_")).map(SimpleGrantedAuthority::new))
//                    .map(GrantedAuthority.class::cast)
//                    .toList();
//        });
//        return jwtAuthenticationConverter;
//    }
//
//    @Bean
//    public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
//        var oidcUserService = new OidcUserService();
//        return userRequest -> {
//            var oidcUser = oidcUserService.loadUser(userRequest);
//            var roles = oidcUser.getClaimAsStringList("realm_access");
//            var authorities = Stream.concat(oidcUser.getAuthorities().stream(),
//                            roles.stream().filter(role -> role.startsWith("ROLE_")).map(SimpleGrantedAuthority::new))
//                    .map(GrantedAuthority.class::cast)
//                    .toList();
//            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
//        };
//    }
}
