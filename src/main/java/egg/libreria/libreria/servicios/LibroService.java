package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Libro;
import egg.libreria.libreria.repositorios.AutorRepositorio;
import egg.libreria.libreria.repositorios.EditorialRepositorio;
import egg.libreria.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepository;

    @Autowired
    private AutorRepositorio autorRepository;

    @Autowired
    private EditorialRepositorio editorialRepository;

    @Transactional
    public void crear(Long isbn, String titulo, Integer anio,
            Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) {

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);

        libro.setAutor(autorRepository.findById(idAutor).orElse(null));
        libro.setEditorial(editorialRepository.findById(idEditorial).orElse(null));

        libroRepository.save(libro);
    }

    @Transactional
    public void modificar(String id, Long isbn, String titulo, Integer anio,
            Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) {
        Libro libro = buscarPorId(id);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAutor(autorRepository.findById(idAutor).orElse(null));
        libro.setEditorial(editorialRepository.findById(idEditorial).orElse(null));
        libroRepository.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarTodas() {
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(String id) {
        Optional<Libro> libroOptional = libroRepository.findById(id);
        return libroOptional.orElse(null);
    }

    @Transactional
    public void eliminar(String id) {
        libroRepository.deleteById(id);
    }

    public void save(Libro libro) {
        libroRepository.save(libro);
    }

    public Libro crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String idAutor, String idEditorial) {
        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);

        libro.setAutor(autorRepository.findById(idAutor).orElse(null));
        libro.setEditorial(editorialRepository.findById(idEditorial).orElse(null));

        return libro;
    }

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    @Transactional
    public void habilitar(String id) {
        Libro libro = buscarPorId(id);
        libro.setAlta(Boolean.TRUE);
        libroRepository.save(libro);
    }

    @Transactional
    public void deshabilitar(String id) {
        Libro libro = buscarPorId(id);
        libro.setAlta(Boolean.FALSE);
        libroRepository.save(libro);
    }

}
