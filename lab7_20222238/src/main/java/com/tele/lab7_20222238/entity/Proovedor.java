package com.tele.lab7_20222238.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Proovedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String razonSocial;

    @NotBlank
    @Size(max = 100)
    private String nombreComercial;

    @NotBlank
    @Pattern(regexp = "\\d{11}")
    @Column(unique = true)
    private String ruc;

    @Pattern(regexp = "\\d+")
    private String telefono;

    @Email
    private String correoElectronico;

    @Pattern(regexp = "^(http|https)://.*$")
    private String sitioWeb;

    @Size(max = 150)
    private String direccionFisica;

    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")
    private String pais;

    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")
    private String representanteLegal;

    @Pattern(regexp = "\\d{8}")
    private String dniRepresentante;

    @Pattern(regexp = "Nacional|Internacional")
    private String tipoProveedor;

    @Pattern(regexp = "Servicios|Productos|Tecnología|Otros")
    private String categoria;

    private BigDecimal facturacionAnual;

    @Column(updatable = false)
    private LocalDateTime fechaRegistro;

    private LocalDateTime ultimaActualizacion;

    private Boolean estado;

    public Proovedor() {}

    @PrePersist
    public void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }


}

