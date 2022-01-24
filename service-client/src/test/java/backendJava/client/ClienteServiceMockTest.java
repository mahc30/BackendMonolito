package backendJava.client;

import backendJava.client.entity.Ciudad;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.Foto;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.repository.ClienteRepository;
import backendJava.client.repository.FotoRepository;
import backendJava.client.service.ClienteService;
import backendJava.client.service.ClienteServiceImpl;
import org.assertj.core.api.Assertions;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ClienteServiceMockTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private FotoRepository fotoRepository;

    private ClienteService clienteService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository, fotoRepository);

        Foto foto = Foto.builder().id("foto123").build();

        Cliente cliente = Cliente.builder()
                .id(5L)
                .nombres("Juanito")
                .apellidos("Alcachofa")
                .numeroIdentificacion("123456")
                .fotoMongoId("foto123")
                .ciudad(Ciudad.Medellin)
                .tipoIdentificacion(TipoIdentificacion.CC)
                .build();

        Mockito.when(clienteRepository.findById(5L))
                .thenReturn(Optional.of(cliente));

        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
        Mockito.when(clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion.CC,"123456" )).thenReturn(cliente);
        Mockito.when(clienteService.createCliente(cliente)).thenReturn(cliente);
        Mockito.when(clienteRepository.findByEdadGreaterThan(24)).thenReturn(Arrays.asList(cliente));
        Mockito.when(clienteRepository.findByEdadGreaterThan(26)).thenReturn(new ArrayList<>());

    }

    @Test
    public void whenValidGetId_ThenReturnCliente(){
        Cliente found = clienteService.getCliente(5L);
        Assertions.assertThat(found.getNombres()).isEqualTo("Juanito");
    }

    @Test
    public void whenValidCreateCliente_ThenReturnCliente(){
        Cliente cliente = Cliente.builder()
                .id(5L)
                .nombres("Juanito")
                .apellidos("Alcachofa")
                .numeroIdentificacion("123456")
                .fotoMongoId("foto123")
                .ciudad(Ciudad.Medellin)
                .tipoIdentificacion(TipoIdentificacion.CC)
                .build();

        Cliente created = clienteService.createCliente(cliente);
        Assertions.assertThat(created.getId()).isEqualTo(cliente.getId());
    }

    @Test
    public void whenValidUpdateCliente_ThenReturnUpdatedCliente(){
        Cliente cliente = clienteService.getCliente(5L);
        cliente.setNombres("Hamilton");

        Cliente updated = clienteService.updateCliente(cliente);
        Assertions.assertThat(updated.getNombres()).isEqualTo("Hamilton");
        Assertions.assertThat(updated.getApellidos()).isEqualTo("Alcachofa");
    }

    @Test
    public void whenValidDeleteCliente_ThenReturnVerifyMethodIsCalled(){

        Cliente cliente = clienteService.getCliente(5L);
        Assertions.assertThat(cliente).isNotNull();
        clienteService.deleteCliente(cliente.getId());
        verify(clienteRepository, times(1)).deleteById(5L);
    }

    @Test
    public void whenValidfindByTipoIdentificacionAndNumeroIdentificacion_ThenReturnClient(){
        Cliente cliente = clienteService.getCliente(5L);
        Cliente found = clienteService.findByTipoIdentificacionAndNumeroIdentificacion(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());
        Assertions.assertThat(cliente).isNotNull();
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getApellidos()).isEqualTo(cliente.getApellidos());
    }

    @Test
    public void whenValidFindByEdadGreaterThan_ThenReturnClientList(){
        List<Cliente> clientes = clienteService.findByEdadGreaterThan(24);

        Assertions.assertThat(clientes.size()).isEqualTo(1);
    }

    @Test
    public void whenFindByEdadGreaterThanAnyClientAge_ThenReturnEmptyList(){
        List<Cliente> clientes = clienteService.findByEdadGreaterThan(26);

        Assertions.assertThat(clientes.size()).isEqualTo(0);
    }
}
