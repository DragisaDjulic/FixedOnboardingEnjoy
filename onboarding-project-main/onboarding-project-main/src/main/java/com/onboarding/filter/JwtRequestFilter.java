package com.onboarding.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.onboarding.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired 
	JwtTokenUtil jwtTokenUtil;
	
//	@Autowired
//	private ApiErrorHandler errorHandler;

	@Autowired
	UserDetailsService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String reqToken = request.getHeader("Authorization");
		
		
		String username = null;
		String jwtToken = null;
		if(reqToken != null && reqToken.startsWith("Bearer ")) {
			jwtToken = reqToken.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//				System.out.println(jwtTokenUtil.getAllClaimsFromToken(jwtToken));
			} 
			catch(IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} 
			catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
//				response.setStatus(HttpStatus.UNAUTHORIZED.value());
//				response.getWriter().write("Expired token!");
			}
			catch(Exception e ) {
//				response.setStatus(HttpStatus.UNAUTHORIZED.value());
//				response.getWriter().write("Invalid token!");
			}
		}else {
			System.out.println("NO TOKEN!");
//			errorHandler.handleApiError(new ApiException("Unauthorized", HttpStatus.UNAUTHORIZED.value()));
//			response.setStatus(HttpStatus.UNAUTHORIZED.value());
//			response.getWriter().write("Unauthorized");
		}
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userService.loadUserByUsername(username);
			
			try {
				if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
				}
			} catch (Exception e) {
				System.out.println("Invalid token!");
			}
		}
		filterChain.doFilter(request, response);
	}

}
