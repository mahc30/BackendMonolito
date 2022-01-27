package backendJava.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FotoDTO {
    private String id;
    private Binary file;
}
