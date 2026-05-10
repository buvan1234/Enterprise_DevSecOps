package com.deploywatch.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/css/**").authenticated()
            .requestMatchers("/api/deployments/**", "/api/incidents/**").authenticated()
            .anyRequest().permitAll())
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));
        return http.build();
    }
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean UserDetailsService userDetailsService(PasswordEncoder encoder){
        return new InMemoryUserDetailsManager(
            User.withUsername("admin").password(encoder.encode("Admin@123")).roles("ADMIN","ENGINEER","VIEWER").build(),
            User.withUsername("engineer").password(encoder.encode("Engineer@123")).roles("ENGINEER","VIEWER").build(),
            User.withUsername("viewer").password(encoder.encode("Viewer@123")).roles("VIEWER").build()
        );
    }
}
