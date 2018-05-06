package hr.tvz.java.teambuildingbooking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by mstuban on 06.05.2018.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**", "/login**", "/register**").permitAll()
                .anyRequest().hasAnyRole("ADMIN", "USER", "MANAGER")
                .filterSecurityInterceptorOncePerRequest(false)
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index", false)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll(true)
                .and()
                .csrf().disable();
    }
}
