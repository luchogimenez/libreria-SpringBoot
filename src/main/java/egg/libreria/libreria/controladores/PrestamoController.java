package egg.libreria.libreria.controladores;

import egg.libreria.libreria.entidades.Prestamo;
import egg.libreria.libreria.servicios.ClienteService;
import egg.libreria.libreria.servicios.LibroService;
import egg.libreria.libreria.servicios.PrestamoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
    @Autowired
    private PrestamoService prestamoService;
    
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private ClienteService clienteService;
    // Crear nuevo prestamo
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Prestamo prestamo){
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.save(prestamo));
    }
    
    // Obtener cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") String id){
        Optional<Prestamo> optionalPrestamo = prestamoService.findById(id);
        
        if(!optionalPrestamo.isPresent()){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(optionalPrestamo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Prestamo prestamoDetails, @PathVariable(value = "id") String prestamoId){
        
        Optional<Prestamo> optionalPrestamo = prestamoService.findById(prestamoId);
        if(!optionalPrestamo.isPresent()){
            return ResponseEntity.notFound().build();
        }
        
        //BeanUtils.copyProperties(prestamoDetails, optionalPrestamo.get()); Copia todas las propiedades
        optionalPrestamo.get().setFechaPrestamo(prestamoDetails.getFechaPrestamo());
        optionalPrestamo.get().setFechaDevolucion(prestamoDetails.getFechaDevolucion());
        optionalPrestamo.get().setLibro(prestamoDetails.getLibro());
        optionalPrestamo.get().setCliente(prestamoDetails.getCliente());
        optionalPrestamo.get().setAlta(prestamoDetails.getAlta());

        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.save(optionalPrestamo.get()));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String prestamoId){
        Optional<Prestamo> optionalPrestamo = prestamoService.findById(prestamoId);
        if(!optionalPrestamo.isPresent()){
            return ResponseEntity.notFound().build();
        }
        prestamoService.deleteById(prestamoId);
         return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public List<Prestamo> readAll(){
        List<Prestamo> prestamos = prestamoService.findAll();
        
        return prestamos;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearPrestamo() {
        ModelAndView mav = new ModelAndView("prestamo-formulario");
        mav.addObject("prestamo", new Prestamo());
        mav.addObject("libros", libroService.findAll());
        mav.addObject("clientes", clienteService.findAll());
        mav.addObject("title", "Crear Prestamo");
        mav.addObject("action", "guardar");
        return mav;
    }
    
    @GetMapping("/mostrar")
    public ModelAndView mostrarPrestamos() {
        ModelAndView mav = new ModelAndView("prestamos");
        mav.addObject("prestamos", readAll());
        return mav;
    }
//    @PostMapping("/crear")
//    public void crearPrestamo(@RequestBody Object datos){
//        System.out.println(datos);
//    }
    
}
