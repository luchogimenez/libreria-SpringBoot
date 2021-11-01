package egg.libreria.libreria.repositorios;

import egg.libreria.libreria.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    @Modifying
    @Query("UPDATE Autor a SET a.nombre = :nombre WHERE a.id = :id")
    void modificar(@Param("id") String id, @Param("nombre") String nombre);
}
