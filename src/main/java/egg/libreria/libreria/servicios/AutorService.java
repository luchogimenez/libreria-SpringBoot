package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Autor;
import egg.libreria.libreria.entidades.Libro;
import egg.libreria.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio autorRepo;

    private Autor autor;

    @Transactional
    public Autor save(Autor autor) {
        return autorRepo.save(autor);
    }

    @Transactional
    public Autor crearAutor(String name) throws Exception {
        
        if (name.isEmpty()) {
            throw new Exception("No ingresó ningun valor");
        }
        
        if (name == null) {
            throw new Exception("Dato nullo");
        }
        
        Pattern p = Pattern.compile("^[a-zA-Z]+( [a-zA-Z]+)*$");
        Matcher m = p.matcher(name);
        if (!m.matches()) {
            throw new Exception("Nombre no válido");
        }
        
        autor = new Autor();
        autor.setNombre(name);

        return autor;
    }

    public List<Autor> listarAutores() {
        return autorRepo.findAll();
    }

    //@Transactional
    public Autor getById(String id) {
        return autorRepo.findById(id).get();
    }

//    public void modificar(String id,String nombre) {
//        Autor autor = getById(id);
//        autor.setNombre(nombre);
//        autorRepo.save(autor);
//    }
    @Transactional
    public void modificar(String id, String nombre) {
        Autor autor = getById(id);
        autor.setNombre(nombre);
        autorRepo.save(autor);
    }

    public void deshabilitar(String id) {
        Autor autor = getById(id);
        autor.setAlta(Boolean.FALSE);
        autorRepo.save(autor);
    }

    public void habilitar(String id) {
        Autor autor = getById(id);
        autor.setAlta(Boolean.TRUE);
        autorRepo.save(autor);
    }

    public List<Autor> listarAutoresHabilitados() {
        List<Autor> lista = listarAutores();
        List<Autor> listaAutoresHabilitados = lista.stream().filter(Autor::getAlta).collect(Collectors.toList());

        return listaAutoresHabilitados;
    }

    @Transactional
    public void changeAlta(Autor autor) {
        if (autor.getAlta()) {
            autor.setAlta(Boolean.FALSE);
        } else {
            autor.setAlta(Boolean.TRUE);
        }
        autorRepo.save(autor);
    }

    @Transactional
    public Optional<Autor> buscarPorId(String id) {
        return autorRepo.findById(id);
    }

    @Transactional
    public void editarAutor(Optional<Autor> autor, Autor autorEdit) {
        // BeanUtils.copyProperties(autorEdit,autor);
        autor.get().setNombre(autorEdit.getNombre());
        //autor.get().setAlta(autorEdit.isAlta());

        autorRepo.save(autor.get());
    }

    @Transactional
    public void eliminar(String id) {
        autorRepo.delete(autorRepo.findById(id).get());
    }

}
