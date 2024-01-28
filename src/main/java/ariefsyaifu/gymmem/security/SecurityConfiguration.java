package ariefsyaifu.gymmem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Autowired
        public SecurityConfiguration(
                        JwtRequestFilter jwtRequestFilter) {
                this.jwtRequestFilter = jwtRequestFilter;
        }

        private JwtRequestFilter jwtRequestFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/api/v1/**")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                // .sessionManagement(sessionManagement -> sessionManagement
                                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .csrf(csrf -> csrf.disable())
                                .addFilterBefore(jwtRequestFilter,
                                                UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

}