// Java
package es.cic.curso.ejerc005.repository;

import es.cic.curso.ejerc005.model.Venta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
        Page<Venta> findByBorradoIsFalse(PageRequest pageRequest);

}