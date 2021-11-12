package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Usuario;
import egg.libreria.libreria.repositorios.UsuarioRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService{
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Transactional
    public void crear(String nombre, String apellido, String correo, String clave) throws Exception{
        if(usuarioRepository.existsUsuarioByCorreo(correo)){
            throw new Exception("Ya existe un usuario asociado al correo ingresado");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreo(correo);
        usuario.setClave(encoder.encode(clave));
        usuario.setAlta(Boolean.TRUE);
        
        usuarioRepository.save(usuario);
    }
    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(()-> new UsernameNotFoundException("No existe un usuario asociado al correo ingresado"));
        return new User(usuario.getCorreo(),usuario.getClave(),Collections.emptyList());        
    }
}
