package pe.com.uba.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity 
@Configuration 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    
	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
				"select username,password,enabled from users where username = ?")
				.authoritiesByUsernameQuery(
						"select username,authority from authorities where username = ? ");
	}
    
    
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  
            .requestCache().requestCache(new CustomRequestCache()) 
            .and().authorizeRequests() 
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()  

            .anyRequest().authenticated()  

            .and().formLogin()  
            .loginPage(LOGIN_URL).permitAll()
            .loginProcessingUrl(LOGIN_PROCESSING_URL)  
            .failureUrl(LOGIN_FAILURE_URL)
            .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL); 
        System.out.println("configure HttpSecurity");
    }
    
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//            User.withUsername("user")
//                .password("{noop}password")
//                .roles("USER")
//                .build();
//        System.out.println("UserDetailsService");
//        return new InMemoryUserDetailsManager(user);
//    }
    
    @SuppressWarnings("deprecation")
	@Bean
    public PasswordEncoder getPasswordEncoder() {
        //        PasswordEncoder encoder = new PasswordEncoder() {
        //            @Override
        //            public String encode(CharSequence charSequence) {
        //                return null;
        //            }
        //
        //            @Override
        //            public boolean matches(CharSequence charSequence, String s) {
        //                return false;
        //            }
        //        }
        return NoOpPasswordEncoder.getInstance();
    }
    
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
            "/VAADIN/**",
            "/favicon.ico",
            "/robots.txt",
            "/manifest.webmanifest",
            "/sw.js",
            "/offline.html",
            "/icons/**",
            "/images/**",
            "/styles/**",
            "/h2-console/**");        
        System.out.println("configure");
    }
    
}