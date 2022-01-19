package backendJava.client;

import backendJava.client.entity.Ciudad;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.Foto;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.repository.FotoRepository;
import backendJava.client.service.ClienteServiceImpl;
import backendJava.client.service.FotoService;
import org.assertj.core.api.Assertions;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
public class FotoServiceMockTest {
    @Autowired
    private FotoService fotoService;

    @Test
    public void whenValidGetAllFoto_ThenReturnListFoto(){
        List<Foto> found = fotoService.listAllFoto();
        Assertions.assertThat(found.size()).isNotEqualTo(0);
        Assertions.assertThat(found.get(found.size()-1).getTipo_archivo()).isNotNull();
    }

    @Test
    public void whenValidGetFotoById_ThenReturnFoto(){
        Foto found = fotoService.getFoto("61e867f7c415d9669e90a155");
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getTipo_archivo()).isEqualTo("jpg");
    }

    @Test
    void whenValidUpdateFoto_ThenReturnUpdatedFoto(){
        Foto fotoDB = fotoService.getFoto("61e8682f2bef496681664616");
        fotoDB.setTipo_archivo("svg");
        Foto updated = fotoService.updateFoto(fotoDB);
        Assertions.assertThat(updated.getTipo_archivo()).isEqualTo(fotoDB.getTipo_archivo());
        fotoDB.setTipo_archivo("jpg");
        updated = fotoService.updateFoto(fotoDB);
        Assertions.assertThat(updated.getTipo_archivo()).isEqualTo(fotoDB.getTipo_archivo());
    }

    @Test
    void whenValidDeleteFoto_ThenGetFotoReturnsNull(){
        List<Foto> fotos = fotoService.listAllFoto();
        Foto last = fotos.get(fotos.size() - 1);
        last.setId(null); //Edit id so mongo creates copy

        Foto upload = fotoService.createFoto(last);
        Assertions.assertThat(upload).isNotNull();

        fotoService.deleteFoto(upload.getId());
        Foto found = fotoService.getFoto(upload.getId());
        Assertions.assertThat(found).isNull();
    }
}
