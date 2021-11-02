package egg.libreria.libreria.controladores;

import egg.libreria.libreria.entidades.Cliente;
import egg.libreria.libreria.servicios.ClienteService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
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

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    // Crear nuevo cliente
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
    }
    
    // Obtener cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") String id){
        Optional<Cliente> optionalCliente = clienteService.findById(id);
        
        if(!optionalCliente.isPresent()){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(optionalCliente);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente clienteDetails, @PathVariable(value = "id") String clienteId){
        
        Optional<Cliente> optionalCliente = clienteService.findById(clienteId);
        if(!optionalCliente.isPresent()){
            return ResponseEntity.notFound().build();
        }
        
        //BeanUtils.copyProperties(clienteDetails, optionalCliente.get()); Copia todas las propiedades
        optionalCliente.get().setApellido(clienteDetails.getApellido());
        optionalCliente.get().setNombre(clienteDetails.getNombre());
        optionalCliente.get().setTelefono(clienteDetails.getTelefono());
        optionalCliente.get().setDocumento(clienteDetails.getDocumento());

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(optionalCliente.get()));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String clienteId){
        Optional<Cliente> optionalCliente = clienteService.findById(clienteId);
        if(!optionalCliente.isPresent()){
            return ResponseEntity.notFound().build();
        }
        clienteService.deleteById(clienteId);
         return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public List<Cliente> readAll(){
        List<Cliente> clientes = clienteService.findAll();
        
        return clientes;
    }
}
