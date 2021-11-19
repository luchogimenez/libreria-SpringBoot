package egg.libreria.libreria.repositorios;

import egg.libreria.libreria.entidades.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    Usuario buscarUsuarioPorCorreo(@Param("correo") String correo);

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}
