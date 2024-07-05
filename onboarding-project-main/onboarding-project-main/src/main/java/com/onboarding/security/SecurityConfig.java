package com.onboarding.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.onboarding.filter.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableScheduling
public class SecurityConfig {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired 
	private JwtAuthEntryPoint jwtAuthEntryPoint;

//	@Autowired
//	private UserServiceImplementation userService;
    
    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public RoleHierarchy roleHierarchy() {
//		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//		String hierarchy = "ROLE_ADMIN > ROLE_MOD \n ROLE_MOD > ROLE_SUBMOD \n ROLE_SUBMOD > ROLE_EMPLOYEE";
//		roleHierarchy.setHierarchy(hierarchy);
//		return roleHierarchy;
//	}
	
//	private SecurityExpressionHandler webExpressionHandler() {
//        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
//        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
//        return defaultWebSecurityExpressionHandler;
//    }
	
//	@Bean
//	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//	    DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//	    expressionHandler.setRoleHierarchy(roleHierarchy());
//	    return expressionHandler;
//	}
//	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//	}
	
	@Bean
	AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}

////	
//	@Bean 
//	UserDetailsManager users(DataSource dataSource) {
//		UserDetails user = User.withDefaultPasswordEncoder().build();
//		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//		users.createUser(user);
//		return users;
//	}
    
//	@Bean
//	public WebSecurityCustomizer wsc() {
//		return (web) -> web.ignoring().antMatchers("/users", "/users/login");
//	}
    
//	@Bean
//	CorsConfigurationSource corsConfig() {
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
//		config.setAllowCredentials(true);
//		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
//		src.registerCorsConfiguration("/**", config);
//		return src;
//	}
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//authentrypoint !!!!!!!!!!!!!
		http.csrf().disable()
			.authorizeHttpRequests((auth) -> auth.antMatchers("/users/login", "/users/reset", "/pdf/generate", "/swagger-ui.html").permitAll()
			.antMatchers(HttpMethod.GET, "/tasks").hasAnyRole("ADMIN", "MOD", "EMPLOYEE")
			.antMatchers("/tasks").hasAnyRole("ADMIN", "MOD")
			.antMatchers(HttpMethod.GET, "/permissions").hasAnyRole("ADMIN", "MOD", "EMPLOYEE")
			.antMatchers("/permissions").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/roles").hasAnyRole("ADMIN", "MOD", "EMPLOYEE")
			.antMatchers("/roles").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/onboardings").hasAnyRole("ADMIN", "MOD", "EMPLOYEE")
			.antMatchers("/onboardings").hasAnyRole("ADMIN", "MOD")
			.antMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "MOD")
			.antMatchers("/users").hasAnyRole("ADMIN")
			.anyRequest().authenticated())
			.cors()//.configurationSource(req -> corsConfig().getCorsConfiguration(req))
			.and()
//			.authorizeRequests().antMatchers("/users", "/users/login").permitAll()
			.exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	//TODO GET USERS ZEZA U DASHBOARD ZA EMPOLOYEEA
	
	//TODO pw 13ss43e5

	//TODO ROLES & PERMISSIONS GET ???
	
}
