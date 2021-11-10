package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Libro;
import egg.libreria.libreria.repositorios.AutorRepositorio;
import egg.libreria.libreria.repositorios.EditorialRepositorio;
import egg.libreria.libreria.repositorios.LibroRepositorio;
import errores.ErrorServicio;
import java.util.Date;
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
//        libro.setEjemplaresRestantes(ejemplaresRestantes);

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
//        libro.setEjemplaresRestantes(ejemplaresRestantes);
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
//        libro.setEjemplaresRestantes(ejemplaresRestantes);

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
    @Transactional
    public void prestarEjemplar(String id){
        Libro libro = buscarPorId(id);
        if(libro.getEjemplaresRestantes()>=1){
            libro.setEjemplaresPrestados(1);
        }
    }
    public void validar (Long isbn, String titulo, Integer anio,
            Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) throws ErrorServicio{
        if(isbn == null){
            throw new ErrorServicio("El isbn no puede ser nulo");
        }
        if(titulo == null || titulo.isEmpty()){
            throw new ErrorServicio("El isbn no puede ser nulo o vacio");
        }
        if(anio == null || anio>(new Date()).getYear()){
            throw new ErrorServicio("El año no puede ser nulo o mayor al año actual");
        }
        if(ejemplares == null || ejemplares<=0){
            throw new ErrorServicio("La cantidad de ejemplares no puede ser nula o menor o igual a cero");
        }
        if(ejemplaresPrestados == null || ejemplaresPrestados>ejemplares){
            throw new ErrorServicio("La cantidad de ejemplares Prestados no puede ser nula o mayor a la cantidad de ejemplares");
        }
        if(ejemplaresRestantes == null || ejemplaresRestantes!=ejemplares-ejemplaresPrestados){
            throw new ErrorServicio("La cantidad de ejemplares Restantes no puede ser nula y debe ser igual a la resta entre ejemplares y ejemplares prestados");
        }
        if(idAutor == null || idAutor.isEmpty()){
            throw new ErrorServicio("El idAutor no puede ser nulo o vacio");
        }
        if(idEditorial == null || idEditorial.isEmpty()){
            throw new ErrorServicio("El idEditorial no puede ser nulo o vacio");
        }
    }
}
