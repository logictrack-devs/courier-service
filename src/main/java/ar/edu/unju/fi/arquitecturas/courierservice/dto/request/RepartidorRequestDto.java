package ar.edu.unju.fi.arquitecturas.courierservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO para la solicitud de registro de un nuevo repartidor en el sistema.
 *
 * <p>
 * Contiene las validaciones minimas para garantizar que los datos
 * obligatorios sean proporcionados en la API REST.
 * </p>
 *
 * @author Equipo LogicTrack Devs
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepartidorRequestDto {

    @NotBlank(message = "El nombre del repartidor es obligatorio.")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El tipo de vehiculo es obligatorio.")
    private String tipoVehiculo;
}
