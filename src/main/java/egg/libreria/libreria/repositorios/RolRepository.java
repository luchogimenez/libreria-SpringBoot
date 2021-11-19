package egg.libreria.libreria.repositorios;

import egg.libreria.libreria.entidades.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
    Optional<Rol> findByNombre(String nombre);
}
