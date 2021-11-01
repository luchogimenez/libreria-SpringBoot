package egg.libreria.libreria.repositorios;

import egg.libreria.libreria.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
/*
    @Modifying
    @Query("UPDATE Libro l SET "
            + "l.isbn = :isbn,"
            + "l.titulo = :titulo,"
            + "l.anio = :anio,"
            + "l.ejemplares = :ejemplares,"
            + "l.ejemplaresPrestados = :ejemplaresPrestados,"
            + "l.ejemplaresRestantes = :ejemplaresRestantes,"
            + "l.idAutor = :idAutor,"
            + "l.idEditorial = :idEditorial"
            + " WHERE l.id = :id")
    void modificar(
            @Param("isbn") Long isbn,
            @Param("titulo") String titulo,
            @Param("anio") Integer anio,
            @Param("ejemplares") Integer ejemplares,
            @Param("ejemplaresPrestados") Integer ejemplaresPrestados,
            @Param("ejemplaresRestantes") Integer ejemplaresRestantes,
            @Param("idAutor") String idAutor,
            @Param("idEditorial") String idEditorial);*/
}
