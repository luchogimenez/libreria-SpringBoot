package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteServiceInterface {
    
    public List<Cliente> findAll();
    
    public Page<Cliente> findAll(Pageable pageable);
    
    public Optional<Cliente> findById(String id);
    
    public Cliente save(Cliente cliente);
    
    public void deleteById(String id);
}
