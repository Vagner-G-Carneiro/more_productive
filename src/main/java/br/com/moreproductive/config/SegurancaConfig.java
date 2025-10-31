package br.com.moreproductive.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {

    private final JwtAutenticacaoFiltro jwtAutenticacaoFiltro;

    public SegurancaConfig(JwtAutenticacaoFiltro jwtAutenticacaoFiltro) {
        this.jwtAutenticacaoFiltro = jwtAutenticacaoFiltro;
    }

    @Bean
        public PasswordEncoder passwordEncoder()
        {
            return new Argon2PasswordEncoder(16, 32, 1, 1000, 50);
            /*Esse números não são seguros, por mais que o Argon2 seja, o salt e o hashLength estão no padrão,
            * mas o número de paralelismo deve ser redefinido após testes e avaliação do hardware, mas 1 é simples e aceitável,
            * o custo de memória para 65536 (64MB) e interações para 2 ou apenas um pouco mais, para não ter tanto custo
            * com memória e processamento desnecessários.
            * A forma com que os parâmetros do algoritmo foram implementados visam somente cumprir o que um mini MVP
            * ou testes simples precisariam, não recomendado para uso em produção de uma aplicação comercializada
            * e disponível no mercado.
            * Att-Vagner Guimarães Caneiro
            * Abraços!*/
        }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                .csrf(configHttp -> configHttp.disable())
                .cors(cors -> cors.configure(httpSecurity))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(autorizacao -> autorizacao
                                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/autenticacao/cadastrar").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/autenticacao/login").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAutenticacaoFiltro, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
