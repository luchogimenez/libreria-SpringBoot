package egg.libreria.libreria.servicios;

import egg.libreria.libreria.entidades.Prestamo;
import egg.libreria.libreria.repositorios.PrestamoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService implements PrestamoServiceInterface{
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
    
    @Override
    @Transactional(readOnly = true)
    public List<Prestamo> findAll() {
        return prestamoRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Prestamo> findAll(Pageable pageable) {
        return prestamoRepositorio.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prestamo> findById(String id) {
        return prestamoRepositorio.findById(id);
    }

    @Override
    @Transactional
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepositorio.save(prestamo);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        prestamoRepositorio.deleteById(id);
    }
}
