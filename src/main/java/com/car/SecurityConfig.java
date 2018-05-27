package com.car;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.car.handler.LoginSuccessHandler;





@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig  extends WebSecurityConfigurerAdapter {


  
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final Logger logger = Logger.getLogger(SecurityConfig.class);
	
   @Autowired
   private LoginSuccessHandler authenticationSuccessHandler;

	
	RequestMatcher csrfRequestMatcher = new RequestMatcher() {

		private String allowedMethod = "GET";

		private AntPathRequestMatcher[] requestMatchers = {
				new AntPathRequestMatcher("/index"),				
				new AntPathRequestMatcher("/login"),
				new AntPathRequestMatcher("/logout"),
				new AntPathRequestMatcher("/models"),
				new AntPathRequestMatcher("/insight/**"),
				new AntPathRequestMatcher("/employees/**"),
				new AntPathRequestMatcher("/register/**"),
				new AntPathRequestMatcher("/customers/**"),
				new AntPathRequestMatcher("/newsletter/**"),
				new AntPathRequestMatcher("/admin/**"),
				new AntPathRequestMatcher("/models/**"),
				new AntPathRequestMatcher("/schedule/**"),
				new AntPathRequestMatcher("/service/**"),
				new AntPathRequestMatcher("/trackemi/**"),
				new AntPathRequestMatcher("/emi/**"),
				new AntPathRequestMatcher("/appointmenthistory/**"),
				new AntPathRequestMatcher("/feedback/**"),
				new AntPathRequestMatcher("/salesappointments/**"),
				new AntPathRequestMatcher("/serviceappointments/**"),
				new AntPathRequestMatcher("/taxmanagement/**"),
				new AntPathRequestMatcher("/emisale/**"),
				new AntPathRequestMatcher("/appointmentsoftheday/**"),
				new AntPathRequestMatcher("/addinventory/**"),
				new AntPathRequestMatcher("/updateinventory/**"),
				new AntPathRequestMatcher("/removeinventory/**"),
				new AntPathRequestMatcher("/customerfeedback/**"),
				new AntPathRequestMatcher("/marketperformance/**"),
				new AntPathRequestMatcher("/servicesoftheday/**")
 };

		@Override
		public boolean matches(HttpServletRequest request) {


		
			for (AntPathRequestMatcher rm : requestMatchers) {
				if (rm.matches(request)) {
					//logger.debug( request.getRequestURI()+ "Matched with" + rm.getPattern());
					//logger.debug("Sec C executed");
					return false;
				}
			}
			if (request.getMethod().equals(allowedMethod)) {
				return false;
			}
			return true;
		}

	};
	/*.successHandler(authenticationSuccessHandler)*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//logger.debug("Sec config is executed!");
		//logger.debug("Sec config is executed!" + rq.getRequestURI());
		http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
		.and()
				.authorizeRequests().antMatchers("/resources/**","/**","/index**","/login**","/register**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login").successHandler(authenticationSuccessHandler).failureUrl("/login")
				.usernameParameter("userId").permitAll()
				.and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();
	
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		  auth.inMemoryAuthentication().withUser("a").password("a").roles("ADMIN");
		 auth.inMemoryAuthentication().withUser("b").password("b").roles("CUSTOMER");
		  //auth.inMemoryAuthentication().withUser("kans").password("kans123").roles("USER");
	}
	
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }
}