package backendJava.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Fotos")
public class Foto implements Serializable {
    @Id
    @NotNull
    @ApiModelProperty(hidden = true)
    private String id;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Binary file;
}
