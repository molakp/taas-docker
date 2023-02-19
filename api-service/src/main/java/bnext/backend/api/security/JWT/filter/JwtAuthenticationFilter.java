package bnext.backend.api.security.JWT.filter;

import bnext.backend.api.security.JWT.JwtUtil;
import bnext.backend.api.security.exceptions.JwtTokenMissingException;
import bnext.backend.api.security.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserAuthService userAuthService;

    @Override
    // Filtro applicato a ogni richiesta ricevuta
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtTokenMissingException("No JWT token found in the request headers");
        }

        String token = header.substring("Bearer".length() + 1);

        // Optional - verification
        jwtUtil.validateToken(token);
        // Recupero l'username passando il token della chiamata
        String userName = jwtUtil.getUserName(token);

        UserDetails userDetails = userAuthService.loadUserByUsername(userName);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

	/*@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {
		String path = request.getRequestURI();
		return "/h2-console/**".equals(path);
	}*/

}
