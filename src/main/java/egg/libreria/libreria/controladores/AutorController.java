package egg.libreria.libreria.controladores;

import egg.libreria.libreria.entidades.Autor;
import egg.libreria.libreria.servicios.AutorService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/autores")
@CrossOrigin(origins = "*")
public class AutorController {
    
    @Autowired
    private AutorService autorService;
    
    @GetMapping
    public ModelAndView autor(){
        ModelAndView mav = new ModelAndView("autores");
        mav.addObject("autores", listaAutores());
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearMascota() {
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor", new Autor());
        mav.addObject("usuarios", listaAutores());
        mav.addObject("title", "Crear Autor");
        mav.addObject("action", "guardar");
        return mav;
    }
   
    @GetMapping("/editar/{id}")
    public ModelAndView editarMascota(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor", autorService.getById(id));
        mav.addObject("title", "Editar Autor");
        mav.addObject("action", "modificar");
        return mav;
    }
    @GetMapping("/lista")
    public List<Autor> listaAutores(){
        return autorService.listarAutores();
    }
    @GetMapping("/listaHabilitados")
    public List<Autor> listaAutoresHabilitados(){
        return autorService.listarAutoresHabilitados();
    }
    @GetMapping("/autorById/{id}")
    public Autor getById(@PathVariable String id){
        return autorService.getById(id);
    }
    
    @PostMapping("/insertar")
    public ResponseEntity<?> crearAutor(@RequestBody Autor autorSchema){
        
        try{
            Autor autor = autorService.crearAutor(autorSchema.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(autorService.save(autor));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/modificar")
    public ResponseEntity<?> modificar(@RequestBody Autor autorSchema){
        autorService.modificar(autorSchema.getId(),autorSchema.getNombre());
        return ResponseEntity.status(HttpStatus.OK).body("Modificado");
    }
    /*@PostMapping("/guardar")
    public RedirectView guardar(@RequestBody String nombre) {
        autorService.crearAutor(nombre);
        return new RedirectView("/autores");
    }*/
    
    @PostMapping("/guardarAutor")
    public void guardarAutor(@RequestBody Autor autor) {
        autorService.save(autor);
        
    }
    
    @PostMapping("/modificar")
    public RedirectView modificarAutor(@RequestParam String id, @RequestParam String nombre) {
        autorService.modificar(id, nombre);
        return new RedirectView("/autores");
    }
    /*@RequestMapping(value = "/editar/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity < String > editarAutor (@RequestBody Autor autorEdit,@PathVariable(value="id") String autorId){
       
        Optional<Autor> autor = autorService.buscarPorId(autorId);
        if(!autor.isPresent()){
             return ResponseEntity.notFound().build();
        }
        autorService.editarAutor(autor, autorEdit);
         return ResponseEntity.status(HttpStatus.OK).build();
         
    }*/
    @PostMapping("/deshabilitar/{id}")
    public RedirectView deshabilitar(@PathVariable String id){
        autorService.deshabilitar(id);
        return new RedirectView("/autores");
    }
    
    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable String id){
        autorService.habilitar(id);
        return new RedirectView("/autores");
    }
    @PostMapping("/changeAlta")
    public void changeAlta(@RequestBody Autor autor){
        autorService.changeAlta(autor);
    }
    @GetMapping("/mostrarAutores")
    public ModelAndView mostrarAutores(){
        ModelAndView mav = new ModelAndView("tables");
        mav.addObject("autores",listaAutoresHabilitados());
        return mav;
    }
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarAutores(@PathVariable String id){
        autorService.eliminar(id);
        return new RedirectView("/autores");
    }

}
