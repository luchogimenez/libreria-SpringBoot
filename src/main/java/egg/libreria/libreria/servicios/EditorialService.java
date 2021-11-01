package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Editorial;
import egg.libreria.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {
    @Autowired
    private EditorialRepositorio editorialRepo;
    
    
    private Editorial editorial;
    
    @Transactional
    public void save(Editorial editorial){
        editorialRepo.save(editorial);
    }
    
    public Editorial crearAutor(String name) {
        editorial = new Editorial();
        editorial.setNombre(name);
        
        return editorial;
    }
    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales(){
        return editorialRepo.findAll();
    }
    //@Transactional
    public Editorial getById(String id) {
        return editorialRepo.findById(id).get();
    }

//    public void modificar(String id,String nombre) {
//        Autor autor = getById(id);
//        autor.setNombre(nombre);
//        autorRepo.save(autor);
//    }
    @Transactional
    public void modificar(String id, String nombre) {
        editorialRepo.modificar(id, nombre);
    }
    public void deshabilitar(String id){
        Editorial editorial = getById(id);
        editorial.setAlta(Boolean.FALSE);
        editorialRepo.save(editorial);
    }
    
    public void habilitar(String id){
        Editorial editorial = getById(id);
        editorial.setAlta(Boolean.TRUE);
        editorialRepo.save(editorial);
    }

    public List<Editorial> listarAutoresHabilitados() {
        List<Editorial> lista = listarEditoriales();
        List<Editorial> listaAutoresHabilitados = lista.stream().filter(Editorial::getAlta).collect(Collectors.toList());
        
        return listaAutoresHabilitados;
    }
    @Transactional
    public void changeAlta(Editorial editorial) {
        if(editorial.getAlta()){
            editorial.setAlta(Boolean.FALSE);
        }else{
            editorial.setAlta(Boolean.TRUE);
        }
        editorialRepo.save(editorial);
    }

    @Transactional
    public Optional<Editorial> buscarPorId(String id){
       return editorialRepo.findById(id);
    }
    
    @Transactional
    public void editarAutor(Optional<Editorial> editorial, Editorial editorialEdit){
       // BeanUtils.copyProperties(autorEdit,autor);
        editorial.get().setNombre(editorialEdit.getNombre());
        //autor.get().setAlta(autorEdit.isAlta());
      
         editorialRepo.save(editorial.get());
    }

    @Transactional
    public void eliminarEditorial(String id) {
        editorialRepo.deleteById(id);
    }
}
