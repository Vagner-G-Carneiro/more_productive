package br.com.moreproductive.config;
import br.com.moreproductive.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoDetailsService(UsuarioRepository usuarioRepository)
    {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findUsuarioByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado " + username));
    }
}
