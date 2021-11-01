package egg.libreria.libreria.controladores;

import egg.libreria.libreria.entidades.Editorial;
import egg.libreria.libreria.servicios.EditorialService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/editoriales")
@CrossOrigin("*")
public class EditorialController {
    
    @Autowired
    private EditorialService editorialService;
    
    @GetMapping
    public ModelAndView autor(){
        ModelAndView mav = new ModelAndView("editoriales");
        mav.addObject("editoriales", listaEditoriales());
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearEditorial() {
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", new Editorial());
        mav.addObject("editoriales", listaEditoriales());
        mav.addObject("title", "Crear Editorial");
        mav.addObject("action", "guardar");
        return mav;
    }
   
    @GetMapping("/editar/{id}")
    public ModelAndView editarEditorial(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", editorialService.getById(id));
        mav.addObject("title", "Editar Editorial");
        mav.addObject("action", "modificar");
        return mav;
    }
    @GetMapping("/lista")
    public List<Editorial> listaEditoriales(){
        return editorialService.listarEditoriales();
    }
    @GetMapping("/listaHabilitados")
    public List<Editorial> listaEditorialesHabilitados(){
        return editorialService.listarAutoresHabilitados();
    }
    @GetMapping("/autorById/{id}")
    public Editorial getById(@PathVariable String id){
        return editorialService.getById(id);
    }
    
    @PostMapping("/insertar")
    public void crearEditorial(@RequestParam String name){
        Editorial editorial = editorialService.crearAutor(name);
        editorialService.save(editorial);
    }
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre) {
        crearEditorial(nombre);
        return new RedirectView("/editoriales");
    }
    
    @PutMapping("/modificar")
    public void modificar(@RequestParam String id,@RequestParam String nombre){
        editorialService.modificar(id,nombre);
    }
    @PostMapping("/modificar")
    public RedirectView modificarEditorial(@RequestParam String id, @RequestParam String nombre) {
        editorialService.modificar(id, nombre);
        return new RedirectView("/editoriales");
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
        editorialService.deshabilitar(id);
        return new RedirectView("/editoriales");
    }
    
    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable String id){
        editorialService.habilitar(id);
        return new RedirectView("/editoriales");
    }
    @PutMapping("/changeAlta")
    public void changeAlta(@RequestBody Editorial editorial){
        editorialService.changeAlta(editorial);
    }
    @GetMapping("/mostrarAutores")
    public ModelAndView mostrarAutores(){
        ModelAndView mav = new ModelAndView("tables");
        mav.addObject("editoriales",listaEditorialesHabilitados());
        return mav;
    }
    @PostMapping("/eliminar/{id}")
    public void eliminarEditoriales(@PathVariable String id){
        editorialService.eliminarEditorial(id);
       
    }
}
