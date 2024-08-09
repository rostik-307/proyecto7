package es.cic.curso.ejerc005.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import es.cic.curso.ejerc005.model.Venta;

@Service
public class OwnershipService {

    public boolean isOwner(Authentication authentication, Long ventaId) {
        // Implementa la lógica para verificar si el usuario es el propietario de la venta
        // Por ejemplo, puedes consultar la base de datos para verificar la propiedad
        // Aquí solo se proporciona un ejemplo básico
        String username = authentication.getName();
        // Supongamos que el propietario de la venta con ID 5 es "user1"
        return "Juan".equals(username) && ventaId != null;
    }
}