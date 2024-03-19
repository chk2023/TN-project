package com._3dhs.tnproject.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                /* CSRF 공격 방지는 기본적으로 활성화 되어 있어 비활성화 처리 */
                .csrf(csrf -> csrf.disable())
                /* 요청에 대한 권한 체크 */
                .authorizeHttpRequests(auth -> {
                    /* 위에 서술 된 패턴 외의 요청은 인증 되지 않은 사용자도 요청 허가 */
                    auth.anyRequest().permitAll();
                })
                /* 로그인 설정 */
                .formLogin(login ->{
                    /* 로그인 페이지 설정 */
                    login.loginPage("/member/login");
                    /* 성공 시 랜딩 페이지 설정 */
                    login.defaultSuccessUrl("/");
                    /* 로그인 실패 시 랜딩 페이지 설정 */
                    login.failureForwardUrl("/error/login");
                })
                /* 로그아웃 설정 */
                .logout(logout ->{
                    /* 로그아웃 요청 URL */
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"));
                    /* JSESSIONID 쿠키 삭제 */
                    logout.deleteCookies("JSESSIONID");
                    /* 세션 만료 */
                    logout.invalidateHttpSession(true);
                    /* 로그아웃 후 랜딩 페이지 */
                    logout.logoutSuccessUrl("/");
                })
                /* 인증/인가 예외 처리 */
                .exceptionHandling(exception -> {
                    /* 인증이 필요할 때는 로그인 페이지로 이동하게 된다. */
                    /* 인가 되지 않았을 때 - 권한이 없는 기능을 요청했을 때 랜딩 될 페이지 */
                    exception.accessDeniedPage("/error/denied");
                })
                .build();

    }
}
