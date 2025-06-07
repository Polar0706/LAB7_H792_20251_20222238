package com.tele.lab7_20222238.controller;

import com.tele.lab7_20222238.entity.Proovedor;
import com.tele.lab7_20222238.repository.ProveedorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@CrossOrigin
public class ProovedorController {

    private final ProveedorRepository proveedorRepository;

    @GetMapping
    public List<Map<String, Object>> listarTodos() {
        return proveedorRepository.findAll().stream().map(p -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", p.getId());
            m.put("razonSocial", p.getRazonSocial());
            m.put("nombreComercial", p.getNombreComercial());
            m.put("ruc", p.getRuc());
            m.put("telefono", p.getTelefono());
            m.put("correo", p.getCorreoElectronico());
            m.put("pais", p.getPais());
            m.put("representanteLegal", p.getRepresentanteLegal());
            m.put("dniRepresentante", p.getDniRepresentante());
            m.put("tipoProveedor", p.getTipoProveedor());
            m.put("categoria", p.getCategoria());
            m.put("estado", p.getEstado() ? "Activo" : "Inactivo");
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Proovedor> opt = proveedorRepository.findById(id);
        if (opt.isPresent()) {
            Proovedor p = opt.get();
            Map<String, Object> m = new LinkedHashMap<>();
            BeanUtils.copyProperties(p, m);
            m.put("estado", p.getEstado() ? "Activo" : "Inactivo");
            return ResponseEntity.ok(m);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Proveedor no encontrado"));
        }
    }


    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Proovedor proveedor) {
        proveedor.setEstado(true);
        proveedor.setUltimaActualizacion(null);
        Proovedor creado = proveedorRepository.save(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("estado", "creado", "id", creado.getId()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Optional<Proovedor> opt = proveedorRepository.findById(id);
        if (opt.isPresent()) {
            Proovedor proveedor = opt.get();

            campos.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Proovedor.class, key);
                if (field != null) {
                    field.setAccessible(true);

                    try {
                        Object convertedValue = convertirValor(value, field.getType());
                        ReflectionUtils.setField(field, proveedor, convertedValue);
                    } catch (Exception e) {
                        System.err.println("Error al actualizar campo " + key + ": " + e.getMessage());
                    }
                }
            });

            Proovedor actualizado = proveedorRepository.save(proveedor);
            return ResponseEntity.ok(Map.of("estado", "actualizado"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Proveedor no encontrado"));
        }
    }

    private Object convertirValor(Object valor, Class<?> tipo) {
        if (valor == null) {
            return null;
        }

        if (tipo == String.class) {
            return valor.toString();
        } else if (tipo == Boolean.class || tipo == boolean.class) {
            return Boolean.valueOf(valor.toString());
        } else if (tipo == Integer.class || tipo == int.class) {
            return Integer.valueOf(valor.toString());
        } else if (tipo == Long.class || tipo == long.class) {
            return Long.valueOf(valor.toString());
        }

        return valor;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        Optional<Proovedor> opt = proveedorRepository.findById(id);
        if (opt.isPresent()) {
            Proovedor proveedor = opt.get();
            proveedor.setEstado(false);
            proveedorRepository.save(proveedor);
            return ResponseEntity.ok(Map.of("estado", "inactivo"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Proveedor no encontrado"));
        }
    }
}
