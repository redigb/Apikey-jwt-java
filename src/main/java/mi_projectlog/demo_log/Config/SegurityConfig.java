package mi_projectlog.demo_log.Config;

import lombok.RequiredArgsConstructor;
import mi_projectlog.demo_log.Jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

// indica metodos - de restricion de rutas y que rutas son publicas
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SegurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    // Configuracion de rutas publicas y privadas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Portecion csrf - seguridad auth en la solicitudes Post - desabilitado
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest ->
                    authRequest
                        //sera publico apartir de la ruta dada - cambio a solicitar jwt
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Configuracion en vase solo para JWT
                .sessionManagement( sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
