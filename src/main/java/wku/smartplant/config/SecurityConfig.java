package wku.smartplant.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wku.smartplant.jwt.JwtTokenFilter;
import wku.smartplant.service.MemberService;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;
//    @Value("${jwt.secret}")
//    private String secretKey;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        requests -> {
                            requests.requestMatchers("/member/login","/member/join").permitAll(); // join, login은 언제나 가능
                            requests.requestMatchers(HttpMethod.GET,"/arduino/admin/**").hasAuthority("ADMIN");
                            requests.requestMatchers(HttpMethod.POST,"/member/**").authenticated(); //인증된 사용자 인지 확인
                            requests.requestMatchers("/arduino/**").authenticated(); //인증된 사용자 인지 확인

                        }
                )
                .addFilterBefore(new JwtTokenFilter(memberService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
