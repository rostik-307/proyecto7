// Java
package es.cic.curso.ejerc005.service;

import es.cic.curso.ejerc005.model.Sofa;
import es.cic.curso.ejerc005.repository.SofaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SofaService {

    @Autowired
    private SofaRepository sofaRepository;

    @PreAuthorize("hasRole('TAQUILLERO')")
    public Sofa añadirSofa(Sofa sofa) {
        return sofaRepository.save(sofa);
    }

    public Optional<Sofa> leerSofa(Long id) {
        return sofaRepository.findById(id);
    }

    @PreAuthorize("hasRole('TAQUILLERO') and @ownershipService.isOwner(authentication, #id)")
    public void devolverSofa(Long id) {
        sofaRepository.deleteById(id);
    }

    public Page<Sofa> listarSofas(int page, int size) {
        return sofaRepository.findByBorradoIsFalse(PageRequest.of(page, size));
    }
}