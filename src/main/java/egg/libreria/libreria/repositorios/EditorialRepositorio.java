package egg.libreria.libreria.repositorios;

import egg.libreria.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    @Modifying
    @Query("UPDATE Editorial e SET e.nombre = :nombre WHERE e.id = :id")
    void modificar(@Param("id") String id, @Param("nombre") String nombre);
}
