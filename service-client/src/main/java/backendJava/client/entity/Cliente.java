package backendJava.client.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull
    @NotEmpty
    private String nombres;

    @NotEmpty
    private String apellidos;
 
    @Column(name = "NUMEROIDENTIFICACION")
    @NotEmpty
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "El número de identificación solo puede contener números") //Validate string is number
    private String numeroIdentificacion;

    @ApiModelProperty(hidden = true)
    @Column(name = "FOTOMONGOID")
    @Builder.Default
    private String fotoMongoId = ""; //String identificación de Mongo

    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    private int edad;

    @Enumerated(EnumType.STRING)
    private Ciudad ciudad;

    @Enumerated(EnumType.STRING)
    private TipoIdentificacion tipoIdentificacion;
}
