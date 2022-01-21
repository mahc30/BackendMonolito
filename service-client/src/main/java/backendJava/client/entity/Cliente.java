package backendJava.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;
    private String nombres;
    private String apellidos;
    @Column(name = "NUMEROIDENTIFICACION")
    private String numeroIdentificacion;

    @Column(name = "FOTOMONGOID")
    private String fotoMongoId; //String identificación de Mongo

    private int edad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ciudad ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TipoIdentificacion tipoIdentificacion;
}
