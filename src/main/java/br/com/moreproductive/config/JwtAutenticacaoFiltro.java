package br.com.moreproductive.config;

import br.com.moreproductive.entities.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Component
public class JwtAutenticacaoFiltro extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AutenticacaoDetailsService autenticacaoDetailsService;

    public JwtAutenticacaoFiltro(JwtService jwtService, AutenticacaoDetailsService autenticacaoDetailsService) {
        this.jwtService = jwtService;
        this.autenticacaoDetailsService = autenticacaoDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autorizacaoHeader = request.getHeader("Authorization");
        if(Strings.isNotEmpty(autorizacaoHeader) && autorizacaoHeader.startsWith("Bearer "))
        {
            String token = autorizacaoHeader.substring("Bearer ".length());
            Optional<JWTUserData> optUsuario = jwtService.validarToken(token);
            if(optUsuario.isPresent())
            {
                JWTUserData userData = optUsuario.get();
                Usuario usuario = (Usuario) autenticacaoDetailsService.loadUserByUsername(userData.email());
                if(usuario.getTokenValido() != null && userData.issuedAtAsInstant().isBefore(usuario.getTokenValido()))
                {
                    throw new AccessDeniedException("Token inválido.");
                }
                UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
            filterChain.doFilter(request, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}
