package egg.libreria.libreria.controladores;

import egg.libreria.libreria.entidades.Cliente;
import egg.libreria.libreria.entidades.Libro;
import egg.libreria.libreria.entidades.Prestamo;
import egg.libreria.libreria.servicios.ClienteService;
import egg.libreria.libreria.servicios.LibroService;
import egg.libreria.libreria.servicios.PrestamoService;
import egg.libreria.libreria.errores.ErrorServicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
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
    public ResponseEntity<?> create(@RequestBody Prestamo prestamo) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.save(prestamo));
        } catch (ErrorServicio err) {
            JSONObject myJson = new JSONObject();
            myJson.put("message", err.getMessage());
            myJson.put("status", "Error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myJson.toString());
        }

    }

    // Obtener cliente por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> read(@PathVariable(value = "id") String id) {
        Optional<Prestamo> optionalPrestamo = prestamoService.findById(id);
        JSONObject myJson = new JSONObject();
        if (!optionalPrestamo.isPresent()) {
            myJson.put("message", "Mesaje de error... no se encuentra el prestamo con ese id");
            myJson.put("status", "Error");
            //"{\"Error\": \"Error al buscar\"}"
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myJson.toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalPrestamo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Prestamo prestamoDetails, @PathVariable(value = "id") String prestamoId) {

        Optional<Prestamo> optionalPrestamo = prestamoService.findById(prestamoId);
        if (!optionalPrestamo.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        //BeanUtils.copyProperties(prestamoDetails, optionalPrestamo.get()); Copia todas las propiedades
        optionalPrestamo.get().setFechaPrestamo(prestamoDetails.getFechaPrestamo());
        optionalPrestamo.get().setFechaDevolucion(prestamoDetails.getFechaDevolucion());
        optionalPrestamo.get().setLibro(prestamoDetails.getLibro());
        optionalPrestamo.get().setCliente(prestamoDetails.getCliente());
        optionalPrestamo.get().setAlta(prestamoDetails.getAlta());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.save(optionalPrestamo.get()));
        } catch (ErrorServicio err) {
            JSONObject myJson = new JSONObject();
            myJson.put("message", err.getMessage());
            myJson.put("status", "Error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myJson.toString());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String prestamoId) {
        Optional<Prestamo> optionalPrestamo = prestamoService.findById(prestamoId);
        if (!optionalPrestamo.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prestamoService.deleteById(prestamoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Prestamo> readAll() {
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

    @PostMapping("/crear")
    public ResponseEntity<?> crearPrestamo(ModelMap model, @RequestBody String datos) {
        JSONObject myJson = new JSONObject(datos);
        JSONObject errorJson = new JSONObject();
        Prestamo prestamo = new Prestamo();
        Cliente cliente = null;
        Libro libro = null;
        Date fechaDevolucion = null;
        try {

            cliente = clienteService.findById(myJson.get("clienteId").toString()).orElse(null);
            libro = libroService.buscarPorId(myJson.get("libroId").toString());
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
            String strFecha = myJson.get("fechaDevolucion").toString();

            fechaDevolucion = formatoDelTexto.parse(strFecha);

            prestamo.setCliente(cliente);
            prestamo.setLibro(libro);
            prestamo.setFechaDevolucion(fechaDevolucion);
            prestamo.setFechaPrestamo(new Date());
            libroService.descontarLibroPrestado(libro);
            prestamoService.save(prestamo);
            model.put("error", "El préstamo se ha creado de manera exitosa");
        } catch (ErrorServicio err) {
            
            errorJson.put("error", err.getMessage());
            errorJson.put("status", "Error");
            model.put("error", err.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJson.toString());
        } catch (ParseException ex) {
            model.put("error", ex.getMessage());
            errorJson.put("error", ex.getMessage());
            errorJson.put("status", "Error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJson.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
    }
    
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitarPrestamo(ModelMap model,@PathVariable(value = "id") String id, @RequestBody String datos) {
        System.out.println(id);
        System.out.println(datos);
        
        return null;
    }
}
