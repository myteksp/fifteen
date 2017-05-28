package com.gf.fifteen;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.gf.fifteen.services.UserService;

@Configuration 
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
public class ScurityConfigurtion extends WebSecurityConfigurerAdapter{
	private final UserService userDetailsService;
	private final RedirectStrategy redirectStrategy;
	
	@Autowired
	public ScurityConfigurtion(
			final UserService userDetailsService){
		this.userDetailsService = userDetailsService;
		this.redirectStrategy = new DefaultRedirectStrategy();
	}
	
	@Override
	protected final void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	@Override
	protected final void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//Opening swagger docs
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated()
		.and()
		//declaring form login and opening it
		.formLogin()
		.loginPage("/login")
		.successHandler(successHandler())
		.failureHandler(authenticationFailureHandler())
		.permitAll()
		.and()
		//Permitting logout
		.logout()
		.logoutSuccessHandler(logoutHandler())
		.permitAll()
		//disable csrf. Since no access from browser available - makes sense.
		.and().httpBasic().and().csrf().disable();
	}

	private final LogoutSuccessHandler logoutHandler(){
		final LogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler(){
			@Override
			public final void onLogoutSuccess(final HttpServletRequest request, 
					final HttpServletResponse response,
					final Authentication authentication) throws IOException, ServletException {
				redirectStrategy.sendRedirect(request, response, "/?logout=" + authentication.getName());
			}
		};
		return handler;
	}

	private final AuthenticationSuccessHandler successHandler() {
		final SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler() {
			@Override
			public final void onAuthenticationSuccess(
					final HttpServletRequest request,
					final HttpServletResponse response,
					final Authentication authentication)
							throws IOException, ServletException {
				redirectStrategy.sendRedirect(request, response, "/");
			}
		};
		return handler;
	}

	private final AuthenticationFailureHandler authenticationFailureHandler(){
		final SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler() {
			@Override
			public final void onAuthenticationFailure(
					final HttpServletRequest request,
					final HttpServletResponse response,
					final org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
				redirectStrategy.sendRedirect(request, response, "/errorView?message=" + exception.getMessage());
			}
		};
		return handler;
	}
}
