package backendJava.client;

import backendJava.client.entity.Foto;
import backendJava.client.repository.FotoRepository;
import org.assertj.core.api.Assertions;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
public class FotoRepositoryMockTest {

    @Autowired
    private FotoRepository fotoRepository;

    @Test
    void findAllTest() {
        List<Foto> found = fotoRepository.findAll();
        Assertions.assertThat(found).isNotEmpty();
    }

    @Test
    void testCreateFoto(){

        byte[] img;
        MultipartFile imagenTest;
        Foto foto;

        try {
            img  = Files.readAllBytes(Paths.get("./testImage.png"));
            imagenTest = new MockMultipartFile("testImage.png", "testImage.png", "png", img);
            foto = Foto.builder()
                    .file(new Binary(BsonBinarySubType.BINARY, imagenTest.getBytes()))
                    .build();
            Foto result = fotoRepository.save(foto);
            Assertions.assertThat(result.getId()).isNotEmpty();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void testDeleteFoto(){

        byte[] img;
        MultipartFile imagenTest;
        Foto foto;

        try {
            img  = Files.readAllBytes(Paths.get("./testImage.png"));
            imagenTest = new MockMultipartFile("testImage.png", "testImage.png", "png", img);
            foto = Foto.builder()
                    .file(new Binary(BsonBinarySubType.BINARY, imagenTest.getBytes()))
                    .build();
            Foto result = fotoRepository.save(foto);
            Assertions.assertThat(result.getId()).isNotEmpty();

            fotoRepository.deleteById(result.getId());
            Assertions.assertThat(fotoRepository.findById(result.getId()).orElse(null)).isEqualTo(null);

        } catch (Exception e){
            System.out.println(e);
        }
    }
}
