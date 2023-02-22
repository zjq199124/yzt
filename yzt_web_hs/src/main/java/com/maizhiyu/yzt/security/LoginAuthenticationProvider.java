package com.maizhiyu.yzt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    private HsUserDetailsService userDetailsService;

    @Autowired
    private void setJdbcUserDetailsService() {
        setUserDetailsService(userDetailsService);
    }

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void initProvider() {
        ReloadableResourceBundleMessageSource localMessageSource = new ReloadableResourceBundleMessageSource();
        localMessageSource.setBasenames("messages_zh_CN");
        messages = new MessageSourceAccessor(localMessageSource);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication){
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }
}
