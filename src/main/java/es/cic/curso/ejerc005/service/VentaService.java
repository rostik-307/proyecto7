// Java
package es.cic.curso.ejerc005.service;

import es.cic.curso.ejerc005.model.Venta;
import es.cic.curso.ejerc005.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @PreAuthorize("hasRole('TAQUILLERO')")
    public Venta añadirVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Optional<Venta> leerVenta(Long id) {
        return ventaRepository.findById(id);
    }

    @PreAuthorize("hasRole('TAQUILLERO') and @ownershipService.isOwner(authentication, #id)")
    public void devolverVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    public Page<Venta> listarVentas(int page, int size) {
        return ventaRepository.findByBorradoIsFalse(PageRequest.of(page, size));
    }
}