package com.coders.hospitalmanagement.config;

import com.coders.hospitalmanagement.security.JwtFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .authorizeHttpRequests(auth -> auth

                // ================= COMMON =================
            		 .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
            		    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ================= AUTH =================
//                .requestMatchers("/auth/**").permitAll()

             // ================= USERS (ADMIN) =================
                .requestMatchers("/users/**")
                    .hasRole("ADMIN")

                    
                // ================= DEPARTMENTS =================
                .requestMatchers(HttpMethod.GET, "/department/**")
                    .hasAnyRole("ADMIN", "DOCTOR", "PATIENT")
                 // ================= DEPARTMENT (ADMIN) =================
                    .requestMatchers(HttpMethod.POST, "/department")
                        .hasRole("ADMIN")

                    .requestMatchers(HttpMethod.DELETE, "/department/**")
                        .hasRole("ADMIN")


                // ================= PATIENT (SELF) =================
                .requestMatchers("/patient/me")
                    .hasRole("PATIENT")

                // ✅ IMPORTANT: allow profile creation
                .requestMatchers(HttpMethod.POST, "/patient/create-profile")
                    .hasRole("PATIENT")

                // ================= PATIENT (ADMIN / NURSE) =================
                .requestMatchers("/patient/search")
                    .hasAnyRole("ADMIN", "NURSE")

                .requestMatchers("/patient/**")
                    .hasAnyRole("ADMIN", "NURSE")

                // ================= DOCTOR =================
                    

//                .requestMatchers("/doctor/me")
//                    .hasRole("DOCTOR")
                    
                    .requestMatchers(HttpMethod.GET, "/doctor")
                    .hasAnyRole("ADMIN", "DOCTOR", "PATIENT","NURSE")

                .requestMatchers(HttpMethod.GET, "/doctor/prescriptions")
                    .hasRole("DOCTOR")

                .requestMatchers(HttpMethod.POST, "/doctor/prescription/**")
                    .hasRole("DOCTOR")
                    

                 // Doctor + Admin endpoints (general)
                    .requestMatchers("/doctor/**")
                    .hasAnyRole("DOCTOR","ADMIN")
                  
                // ================= APPOINTMENT =================
                .requestMatchers("/appointment/my")
                    .hasRole("PATIENT")

                .requestMatchers("/appointment/doctor/my")
                    .hasRole("DOCTOR")

                .requestMatchers(HttpMethod.GET, "/appointment/pending")
                    .hasRole("NURSE")

                .requestMatchers(HttpMethod.GET, "/appointment/today")
                    .hasRole("NURSE")

                .requestMatchers(HttpMethod.GET, "/appointment/last/**")
                    .hasAnyRole("NURSE", "DOCTOR", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/appointment/**")
                    .hasAnyRole("PATIENT", "ADMIN", "NURSE")

                .requestMatchers(HttpMethod.GET, "/appointment/**")
                    .hasAnyRole("ADMIN", "DOCTOR")

                // ================= PRESCRIPTION =================
                .requestMatchers("/prescription/my")
                    .hasRole("PATIENT")

                // ================= BILL =================
                .requestMatchers("/bill/my")
                    .hasRole("PATIENT")

                .requestMatchers("/bill/**")
                    .hasAnyRole("ADMIN", "DOCTOR")

                // ================= FALLBACK =================
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
