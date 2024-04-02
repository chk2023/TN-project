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
                    //auth.requestMatchers("/").permitAll(); //main 페이지와 login 페이지는 모두에게 허용.
                    auth.requestMatchers("/member/login").permitAll(); //main 페이지와 login 페이지는 모두에게 허용.
                    auth.requestMatchers("/member/regist").hasRole("ANONYMOUS"); //회원가입 페이지 모두에게 허용.
                    auth.requestMatchers("/member/idDupCheck").permitAll();
                    auth.requestMatchers("/member/idCheck").permitAll();
                    auth.requestMatchers("/mail").permitAll();
                    auth.requestMatchers("/member/findPwd").permitAll();
                    //인가 범위 추가
                    auth.requestMatchers("/timeline").hasAnyRole("ADMIN", "COMMON");
                    auth.requestMatchers("/manager").hasRole("ADMIN");
                    auth.requestMatchers("/post").hasAnyRole("ADMIN", "COMMON");


//                    auth.requestMatchers("/admin").hasRole("ADMIN"); //ADMIN이라는 role을 가진 경우에만 허용
//                    auth.requestMatchers("/my/**").hasAnyRole("ADMIN", "USER"); // /my/ 뒤에는 여러 유저의 아이디가 올 수 있는데 일일이 지정할 수 없으니 와일드카드 처리.
                    auth.anyRequest().authenticated(); /* 위에 서술 된 패턴 외의 요청은 로그인한 사용자만 요청 허가 */
                })
                /* 로그인 설정 */
                .formLogin(login ->{
                    /* 로그인 페이지 설정 */
                    login.loginPage("/member/login");
//                    login.loginProcessingUrl("/main");
//                    login.permitAll();
                    /* 성공 시 랜딩 페이지 설정 */
                    login.defaultSuccessUrl("/timeline/list?viewType=blog&contentsType=1");
                    /* 로그인 실패 시 랜딩 페이지 설정 */
                    login.failureForwardUrl("/member/loginfail");
                    /* 파라미터명 변경 */
                    login.usernameParameter("memberId");
                    login.passwordParameter("memberPwd");
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
                    logout.logoutSuccessUrl("/member/login");
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
