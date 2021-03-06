package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Prestamo;
import egg.libreria.libreria.errores.ErrorServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrestamoServiceInterface {
    public List<Prestamo> findAll();
    
    public Page<Prestamo> findAll(Pageable pageable);
    
    public Optional<Prestamo> findById(String id);
    
    public Prestamo save(Prestamo prestamo) throws ErrorServicio;
    
    public void deleteById(String id);
}
