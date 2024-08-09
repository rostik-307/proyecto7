// Java
package es.cic.curso.ejerc005.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numeroEntradas;
    private LocalDateTime fechaHoraVenta;
    private double precioPorEntrada;
    private int numeroEntradasDescuentoJoven;
    private boolean descuentoGrupo;
    private Long salaId;
    private Long sesionId;
    private double precioTotal;

    private boolean borrado;

    public boolean isBorrado() {
        return borrado;
    }
    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getNumeroEntradas() {
        return numeroEntradas;
    }
    public void setNumeroEntradas(int numeroEntradas) {
        this.numeroEntradas = numeroEntradas;
    }
    public LocalDateTime getFechaHoraVenta() {
        return fechaHoraVenta;
    }
    public void setFechaHoraVenta(LocalDateTime fechaHoraVenta) {
        this.fechaHoraVenta = fechaHoraVenta;
    }
    public double getPrecioPorEntrada() {
        return precioPorEntrada;
    }
    public void setPrecioPorEntrada(double precioPorEntrada) {
        this.precioPorEntrada = precioPorEntrada;
    }
    public int getNumeroEntradasDescuentoJoven() {
        return numeroEntradasDescuentoJoven;
    }
    public void setNumeroEntradasDescuentoJoven(int numeroEntradasDescuentoJoven) {
        this.numeroEntradasDescuentoJoven = numeroEntradasDescuentoJoven;
    }
    public boolean isDescuentoGrupo() {
        return descuentoGrupo;
    }
    public void setDescuentoGrupo(boolean descuentoGrupo) {
        this.descuentoGrupo = descuentoGrupo;
    }
    public Long getSalaId() {
        return salaId;
    }
    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }
    public Long getSesionId() {
        return sesionId;
    }
    public void setSesionId(Long sesionId) {
        this.sesionId = sesionId;
    }
    public double getPrecioTotal() {
        return precioTotal;
    }
    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Venta other = (Venta) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    public String toString() {
        return "Venta [id=" + id + ", numeroEntradas=" + numeroEntradas + ", fechaHoraVenta=" + fechaHoraVenta
                + ", precioPorEntrada=" + precioPorEntrada + ", numeroEntradasDescuentoJoven="
                + numeroEntradasDescuentoJoven + ", descuentoGrupo=" + descuentoGrupo + ", salaId=" + salaId
                + ", sesionId=" + sesionId + ", precioTotal=" + precioTotal + "]";
    }
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}