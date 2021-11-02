package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Cliente;
import egg.libreria.libreria.repositorios.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService implements ClienteServiceInterface {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepositorio.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(String id) {
        return clienteRepositorio.findById(id);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        clienteRepositorio.deleteById(id);
    }
    
}
