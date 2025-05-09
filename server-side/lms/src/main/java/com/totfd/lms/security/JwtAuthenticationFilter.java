//package com.totfd.lms.security;
//
//import com.totfd.lms.service.JwtService;
//import com.totfd.lms.entity.Users;
//import com.totfd.lms.repository.UsersRepository;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//import java.io.IOException;
//import java.util.Collections;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UsersRepository usersRepository;
//
//    public JwtAuthenticationFilter(JwtService jwtService, UsersRepository usersRepository) {
//        this.jwtService = jwtService;
//        this.usersRepository = usersRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String token = authorizationHeader.substring(7);  // Extract token after "Bearer "
//
//            try {
//                Claims claims = Jwts.parserBuilder()  // Updated parserBuilder for newer JJWT versions
//                        .setSigningKey(jwtService.getJwtSecret())  // Get secret from JwtService
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//
//                String email = claims.getSubject();
//
//                // Authenticate the user based on the JWT claims (email)
//                if (email != null) {
//                    Users user = usersRepository.findByEmail(email)
//                            .orElseThrow(() -> new RuntimeException("User not found"));
//
//                    // Create an authentication token
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            user, null, Collections.emptyList());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    // Set authentication in security context
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            } catch (Exception e) {
//                // Log error or handle token validation exceptions
//                System.out.println("JWT Token is invalid or expired: " + e.getMessage());
//            }
//        }
//
//        // Continue with the request processing
//        filterChain.doFilter(request, response);
//    }
//}
