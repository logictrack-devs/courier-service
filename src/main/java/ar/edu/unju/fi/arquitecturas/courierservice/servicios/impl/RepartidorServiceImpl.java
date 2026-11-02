package ar.edu.unju.fi.arquitecturas.courierservice.servicios.impl;


import ar.edu.unju.fi.arquitecturas.courierservice.dto.request.RepartidorRequestDto;
import ar.edu.unju.fi.arquitecturas.courierservice.dto.response.RepartidorResponseDto;
import ar.edu.unju.fi.arquitecturas.courierservice.enums.EstadoRepartidor;
import ar.edu.unju.fi.arquitecturas.courierservice.excepcion.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitecturas.courierservice.modelo.Repartidor;
import ar.edu.unju.fi.arquitecturas.courierservice.repositorio.RepartidorRepository;
import ar.edu.unju.fi.arquitecturas.courierservice.servicios.RepartidorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RepartidorServiceImpl implements RepartidorService {

    private final RepartidorRepository repartidorRepository;


    @Override
    @Transactional
    public RepartidorResponseDto registrarRepartidor(RepartidorRequestDto request) {
        Repartidor repartidor = Repartidor.builder()
                .nombre(request.getNombre())
                .tipoVehiculo(request.getTipoVehiculo())
                .estado(EstadoRepartidor.ACTIVO)
                .build();

        Repartidor guardado = repartidorRepository.save(repartidor);
        return mapearADto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RepartidorResponseDto> listarTodosPaginado(Pageable pageable) {
        return repartidorRepository.findAll(pageable)
                .map(this::mapearADto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RepartidorResponseDto> listarDisponibles() {
        return repartidorRepository.findByEstado(EstadoRepartidor.ACTIVO)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RepartidorResponseDto obtenerPorId(UUID id) {
        Repartidor repartidor = repartidorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el repartidor con el ID: " + id));
        return mapearADto(repartidor);
    }

    @Override
    @Transactional
    public RepartidorResponseDto actualizar(UUID id, RepartidorRequestDto request) {
        Repartidor repartidor = buscarOLanzar(id);
        repartidor.setNombre(request.getNombre());
        repartidor.setTipoVehiculo(request.getTipoVehiculo());
        return mapearADto(repartidorRepository.save(repartidor));
    }

    @Override
    @Transactional
    public RepartidorResponseDto cambiarEstado(UUID id, EstadoRepartidor estado) {
        Repartidor repartidor = buscarOLanzar(id);
        repartidor.setEstado(estado);
        return mapearADto(repartidorRepository.save(repartidor));
    }

    private Repartidor buscarOLanzar(UUID id) {
        return repartidorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el repartidor con el ID: " + id));
    }


    private RepartidorResponseDto mapearADto(Repartidor repartidor) {
        return RepartidorResponseDto.builder()
                .id(repartidor.getId())
                .nombre(repartidor.getNombre())
                .tipoVehiculo(repartidor.getTipoVehiculo())
                .estado(repartidor.getEstado())
                .build();
    }
}
