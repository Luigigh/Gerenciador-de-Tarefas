package br.com.luigifalconi.taskmanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailService customUserDetailService) {

        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    System.out.println(
            "[JWT FILTER] Requisição: "
                    + request.getMethod()
                    + " "
                    + request.getRequestURI()
    );

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {

        System.out.println(
                "[JWT FILTER] Token não encontrado"
        );

        filterChain.doFilter(request, response);
        return;
    }

    System.out.println(
            "[JWT FILTER] Token encontrado"
    );

    String jwtToken = authHeader.substring(7);

    String username = jwtService.extractUsername(jwtToken);

    System.out.println(
            "[JWT FILTER] Username: " + username
    );

    UserDetails userDetails =
            customUserDetailService.loadUserByUsername(username);

    if (jwtService.isTokenValid(jwtToken, userDetails)) {

        System.out.println(
                "[JWT FILTER] Token válido"
        );

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        System.out.println(
                "[JWT FILTER] Usuário autenticado no SecurityContext"
        );

    } else {

        System.out.println(
                "[JWT FILTER] Token inválido"
        );
    }

    filterChain.doFilter(request, response);
}
}
