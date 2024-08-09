// Java
package es.cic.curso.ejerc005.repository;

import es.cic.curso.ejerc005.model.Sofa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SofaRepository extends JpaRepository<Sofa, Long> {
        Page<Sofa> findByBorradoIsFalse(PageRequest pageRequest);

}