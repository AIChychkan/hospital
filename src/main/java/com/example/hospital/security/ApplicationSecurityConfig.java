package com.example.hospital.security;

import com.example.hospital.Authentication.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.hospital.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }


    //The order of antMatchers matter
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("index", "css/*", "js/*").permitAll() //whitelisting URLs from authentication
                .antMatchers("/patient/**").hasRole(ADMIN.name())//provide access to URL only for specific users
/*                .antMatchers(HttpMethod.DELETE, "/management/**").hasAuthority(PATIENT_REGISTER.getPermission())
                .antMatchers(HttpMethod.POST, "/management/**").hasAuthority(PATIENT_REGISTER.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/**").hasAuthority(PATIENT_REGISTER.getPermission())
                .antMatchers(HttpMethod.GET, "/management/**").hasAnyRole(
                        ADMIN.name(), PATIENT.name(), DOCTOR.name(), NURSE.name())*/
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic(); //basic authentication, which does not let us to logout.
                .formLogin() //form-based authentication.
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/doctors", true)
                    .passwordParameter("password") // "name" in .html
                    .usernameParameter("username") // "name" in .html
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)) //remember-me 30 days.
                    .key("somekey")
                .rememberMeParameter("remember-me") // "name" in .html
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me") //clearing cookies: json-session-id and remember-me
                    .logoutSuccessUrl("/login");
    }

/*    @Override
    @Bean
    protected UserDetailsService userDetailsService() { //how you retrieve a user from DB

        UserDetails marchUser = User.builder()
                .username("march")
                .password(passwordEncoder.encode("0000"))
//                .roles(ApplicationUserRole.PATIENT.name())
                .authorities(PATIENT.getGrantedAuthorities())
                .build();

        UserDetails bartUser = User.builder()
                .username("bart")
                .password(passwordEncoder.encode("0000"))
//                .roles(ApplicationUserRole.PATIENT.name())
                .authorities(PATIENT.getGrantedAuthorities())
                .build();

        UserDetails juliusUser = User.builder()
                .username("julius")
                .password(passwordEncoder.encode("0000"))
//                .roles(ApplicationUserRole.DOCTOR.name())
                .authorities(DOCTOR.getGrantedAuthorities())
                .build();

        UserDetails nickUser = User.builder()
                .username("nick")
                .password(passwordEncoder.encode("0000"))
//                .roles(ApplicationUserRole.NURSE.name())
                .authorities(NURSE.getGrantedAuthorities())
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("0000"))
//                .roles(ApplicationUserRole.ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(//it doesn't take users from DB. It takes users from UserDetailsService each time when the app starts
                bartUser,
                marchUser,
                juliusUser,
                nickUser,
                adminUser);

    }*/ //we dont' need this anymore because we need to connect to realDB


    @Override //this method is for wiring things up
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){ //provider
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(applicationUserService);

        return daoAuthenticationProvider;
    }

}
