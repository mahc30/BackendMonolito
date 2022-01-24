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

    private String testImageId;

    @Test
    public void whenValidGetAllFoto_ThenReturnListFoto(){
        List<Foto> found = fotoService.listAllFoto();
        Assertions.assertThat(found.size()).isNotEqualTo(0);
        Assertions.assertThat(found.get(found.size()-1).getFile()).isNotNull();
    }

    @Test
    public void whenValidGetFotoById_ThenReturnFoto(){
        List<Foto> founds = fotoService.listAllFoto();
        Foto found = fotoService.getFoto(founds.get(0).getId());
        Assertions.assertThat(found).isNotNull();
    }

    @Test
    void whenValidUpdateFoto_ThenReturnUpdatedFoto(){
        List<Foto> fotos = fotoService.listAllFoto();
        Foto fotoDB = fotoService.getFoto(fotos.get(fotos.size() - 1).getId());
        Binary original = fotoDB.getFile();

        byte[] img;
        MultipartFile imagenTest;

        try {
            img  = Files.readAllBytes(Paths.get("./grupo_imagen.jpg"));
            imagenTest = new MockMultipartFile("grupo_imagen.jpg", "grupo_imagen.jpg", "jpg", img);
            Binary newPic = new Binary(BsonBinarySubType.BINARY, imagenTest.getBytes());
            fotoDB.setFile(newPic);
            Foto result = fotoService.updateFoto(fotoDB);

            Assertions.assertThat(result.getId()).isEqualTo(fotoDB.getId());
            Assertions.assertThat(result.getFile()).isEqualTo(newPic);

            fotoDB.setFile(original);
            result = fotoService.updateFoto(fotoDB);

            Assertions.assertThat(result.getFile()).isEqualTo(original);
        } catch (Exception e){
            System.out.println(e);
        }

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
