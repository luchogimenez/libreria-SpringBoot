package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Usuario;
import egg.libreria.libreria.repositorios.RolRepository;
import egg.libreria.libreria.repositorios.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void crear(String nombre, String apellido, String correo, String clave) throws Exception {
        if (usuarioRepository.existsByCorreo(correo)) {
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

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario findByCorreo(String correo) {

        Usuario usuario = null;
        try {
            System.out.println("############################################");
            System.out.println(correo);
            System.out.println("############################################");
            usuario = usuarioRepository.buscarUsuarioPorCorreo(correo);
            System.out.println(usuario);
        } catch (Exception e) {
            System.out.println("############################################");
            System.out.println(e.getMessage());
            System.out.println("############################################");
        }
        System.out.println("############################################");
        System.out.println("SIN EXCEPCION");
        System.out.println("############################################");
        return usuario;
    }

    @Transactional
    public void crear(Usuario dto) throws Exception {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new Exception("Ya existe un usuario asociado al correo ingresado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setClave(encoder.encode(dto.getClave()));
        usuario.setRol(dto.getRol());
        usuario.setCreacion(LocalDateTime.MIN);
        usuario.setAlta(true);

        usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario asociado al correo ingresado"));
        return new User(usuario.getCorreo(), usuario.getClave(), Collections.emptyList());
    }
}
