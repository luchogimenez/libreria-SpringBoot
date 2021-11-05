package egg.libreria.libreria.controladores;

import egg.libreria.libreria.entidades.Cliente;
import egg.libreria.libreria.servicios.ClienteService;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Crear nuevo cliente
    @PostMapping//(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Cliente cliente) {
        //JSONObject myJson = new JSONObject(json);

//        System.out.println(myJson.get("nombre"));
//        System.out.println(myJson.get("apellido"));
//        System.out.println(myJson.get("documento"));
//        System.out.println(myJson.get("telefono"));
//        return null;
        System.out.println(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
    }

    // Obtener cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") String id) {
        Optional<Cliente> optionalCliente = clienteService.findById(id);

        if (!optionalCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente clienteDetails, @PathVariable(value = "id") String clienteId) {

        Optional<Cliente> optionalCliente = clienteService.findById(clienteId);
        if (!optionalCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        //BeanUtils.copyProperties(clienteDetails, optionalCliente.get()); Copia todas las propiedades
        optionalCliente.get().setApellido(clienteDetails.getApellido());
        optionalCliente.get().setNombre(clienteDetails.getNombre());
        optionalCliente.get().setTelefono(clienteDetails.getTelefono());
        optionalCliente.get().setDocumento(clienteDetails.getDocumento());
        optionalCliente.get().setAlta(clienteDetails.getAlta());

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(optionalCliente.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String clienteId) {
        Optional<Cliente> optionalCliente = clienteService.findById(clienteId);
        if (!optionalCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deleteById(clienteId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Cliente> readAll() {
        List<Cliente> clientes = clienteService.findAll();

        return clientes;
    }

    @GetMapping("/crear")
    public ModelAndView crearCliente() {
        ModelAndView mav = new ModelAndView("cliente-formulario");
        mav.addObject("cliente", new Cliente());
        mav.addObject("title", "Crear Cliente");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/mostrar")
    public ModelAndView mostrarClientes() {
        ModelAndView mav = new ModelAndView("clientes");
        mav.addObject("clientes", readAll());
        return mav;
    }
    @GetMapping("/editar/{id}")
    public ModelAndView editarCliente(@PathVariable(value = "id") String id) {
        ModelAndView mav = new ModelAndView("cliente-formulario");
        mav.addObject("cliente",read(id).getBody() );
        mav.addObject("title", "Modificar Cliente");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    @PutMapping("/editar/{id}")
    public void editarCliente(@RequestBody Cliente cliente, @PathVariable(value = "id") String clienteId) {
        //update(clienteDetails,clienteId);
        System.out.println(cliente.getApellido());
        System.out.println(cliente.getDocumento());
        System.out.println(cliente.getNombre());
        System.out.println(cliente.getTelefono());
        
    }
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitarCliente(@PathVariable(value = "id") String clienteId) {
        //update(clienteDetails,clienteId);
        Optional<Cliente> opCliente = clienteService.findById(clienteId);
        if (!opCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        opCliente.get().setAlta(Boolean.FALSE);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(opCliente.get()));
    }
}
