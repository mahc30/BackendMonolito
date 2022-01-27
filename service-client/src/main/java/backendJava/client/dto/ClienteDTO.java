package backendJava.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ClienteDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String numeroIdentificacion;
    private String fotoMongoId; //String identificación de Mongo
    private int edad;
    private String ciudad;
    private String tipoIdentificacion;
}
