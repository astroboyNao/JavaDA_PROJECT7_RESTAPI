package com.nnk.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.Collection;

@Configuration
@EnableWebSecurity(debug = true)
@ComponentScan(basePackages = {"com.nnk.springboot"}
)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TestApplicationConfig  {

    @Bean
    @Primary
    public UserDetailsService myUserDetailsService() {

        UserDetails user = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.asList(new SimpleGrantedAuthority("USER"));
            }

            @Override
            public String getPassword() {
                return "password";
            }

            @Override
            public String getUsername() {
                return "username";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        UserDetails admin = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
            }

            @Override
            public String getPassword() {
                return "password";
            }

            @Override
            public String getUsername() {
                return "adminname";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        return new InMemoryUserDetailsManager(Arrays.asList(
                user, admin
        ));
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}
