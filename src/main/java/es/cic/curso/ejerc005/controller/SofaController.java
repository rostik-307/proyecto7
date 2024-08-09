// Java
package es.cic.curso.ejerc005.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.cic.curso.ejerc005.model.Sofa;
import es.cic.curso.ejerc005.service.SofaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/sofas")
public class SofaController {

    @Autowired
    private SofaService sofaService;

    @PostMapping
    public ResponseEntity<Sofa> añadirSofa(@RequestBody Sofa sofa) {
        Sofa sofaAñadida = sofaService.añadirSofa(sofa);
        return ResponseEntity.status(HttpStatus.CREATED).body(sofaAñadida);
    }

    @GetMapping("/{id}")
    public Optional<Sofa> leerSofa(@PathVariable Long id) {
        return sofaService.leerSofa(id);
    }

    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void devolverSofa(@PathVariable Long id) {
        sofaService.devolverSofa(id);
    }

    @GetMapping
    public Page<Sofa> listarSofas(@RequestParam int page, @RequestParam int size) {
        return sofaService.listarSofas(page, size);
    }
}