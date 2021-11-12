package egg.libreria.libreria.controladores;
import egg.libreria.libreria.entidades.Libro;
import egg.libreria.libreria.servicios.AutorService;
import egg.libreria.libreria.servicios.EditorialService;
import egg.libreria.libreria.servicios.LibroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/libros")
public class LibroController {
    
    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    @GetMapping
    public ModelAndView mostrarTodas() {
        ModelAndView mav = new ModelAndView("libros");
        mav.addObject("libros", lista());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearLibro() {
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", new Libro());
        mav.addObject("autores", autorService.listarAutores());
        mav.addObject("editoriales", editorialService.listarEditoriales());
        mav.addObject("title", "Crear Libro");
        mav.addObject("action", "guardar");
       
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLibro(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", libroService.buscarPorId(id));
        mav.addObject("autores", autorService.listarAutores());
        mav.addObject("editoriales", editorialService.listarEditoriales());
        mav.addObject("title", "Editar Libro");
        mav.addObject("action", "modificar");
        return mav;
    }
    @PostMapping("/insertar")
    public void crearLibro(@RequestParam Long isbn,@RequestParam String titulo,
            @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados,
            @RequestParam Integer ejemplaresRestantes, @RequestParam String idAutor,
            @RequestParam String idEditorial){
            Libro libro = libroService.crearLibro(isbn,titulo,anio
            ,ejemplares, ejemplaresPrestados,
            ejemplaresRestantes,idAutor,idEditorial);
        libroService.save(libro);
    }
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn,@RequestParam String titulo,
            @RequestParam Integer anio, @RequestParam Integer ejemplares, 
            @RequestParam Integer ejemplaresPrestados, @RequestParam("autor") String idAutor,
            @RequestParam("editorial") String idEditorial) {
        libroService.crear(isbn, titulo, anio, ejemplares, ejemplaresPrestados, 
                idAutor, idEditorial);
        return new RedirectView("/libros");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id,@RequestParam Long isbn,@RequestParam String titulo,
            @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados,
            @RequestParam Integer ejemplaresRestantes, @RequestParam("autor") String idAutor,
            @RequestParam("editorial") String idEditorial) {
        libroService.modificar(id,isbn,titulo,anio
            ,ejemplares, ejemplaresPrestados,
            ejemplaresRestantes,idAutor,idEditorial);
        return new RedirectView("/libros");
    }

    @DeleteMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) {
        libroService.eliminar(id);
        return new RedirectView("/libros");
    }
    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable String id){
        libroService.habilitar(id);
        return new RedirectView("/libros");
    }
    @PostMapping("/deshabilitar/{id}")
    public RedirectView deshabilitar(@PathVariable String id){
        libroService.deshabilitar(id);
        return new RedirectView("/libros");
    }
    @GetMapping("/lista")
    public List<Libro> lista(){
        return libroService.findAll();
    }
}
