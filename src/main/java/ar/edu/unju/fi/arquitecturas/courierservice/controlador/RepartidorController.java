package ar.edu.unju.fi.arquitecturas.courierservice.controlador;


import ar.edu.unju.fi.arquitecturas.courierservice.dto.request.RepartidorRequestDto;
import ar.edu.unju.fi.arquitecturas.courierservice.dto.response.RepartidorResponseDto;
import ar.edu.unju.fi.arquitecturas.courierservice.enums.EstadoRepartidor;
import ar.edu.unju.fi.arquitecturas.courierservice.servicios.RepartidorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para exponer los endpoints de gestión de repartidores.
 *
 * @author Equipo LogicTrack Devs
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/repartidores")
@RequiredArgsConstructor
public class RepartidorController {

    private final RepartidorService repartidorService;

    @PostMapping
    public ResponseEntity<RepartidorResponseDto> registrarRepartidor(@Valid @RequestBody RepartidorRequestDto request) {
        RepartidorResponseDto nuevoRepartidor = repartidorService.registrarRepartidor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRepartidor);
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<RepartidorResponseDto>> listarTodosPaginado(Pageable pageable) {
        return ResponseEntity.ok(repartidorService.listarTodosPaginado(pageable));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<RepartidorResponseDto>> listarDisponibles() {
        return ResponseEntity.ok(repartidorService.listarDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepartidorResponseDto> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(repartidorService.obtenerPorId(id));
    }

    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<Boolean> verificarDisponibilidad(@PathVariable UUID id) {
        RepartidorResponseDto repartidor = repartidorService.obtenerPorId(id);
        boolean disponible = (repartidor != null && repartidor.getEstado() == EstadoRepartidor.ACTIVO);
        return ResponseEntity.ok(disponible);
    }
}
