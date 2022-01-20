package backendJava.client;

import backendJava.client.entity.Ciudad;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.repository.ClienteRepository;
import backendJava.client.service.ClienteService;
import backendJava.client.service.ClienteServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ClienteServiceMockTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteService clienteService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository);

        Cliente cliente = Cliente.builder()
                .id(5L)
                .nombres("Juanito")
                .apellidos("Alcachofa")
                .numeroIdentificacion("188941981")
                .fotoMongoId("TODO")
                .ciudad(Ciudad.builder().id(1L).build())
                .tipoIdentificacion(TipoIdentificacion.builder().id(2L).build())
                .build();

        Mockito.when(clienteRepository.findById(5L))
                .thenReturn(Optional.of(cliente));

        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        Mockito.when(clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion.builder().id(2L).build(),"188941981" )).thenReturn(cliente);
    }

    @Test
    public void whenValidGetId_ThenReturnCliente(){
        Cliente found = clienteService.getCliente(5L);
        Assertions.assertThat(found.getNombres()).isEqualTo("Juanito");
    }

    @Test
    public void whenValidCreateCliente_ThenReturnCliente(){
        Cliente cliente =  Cliente.builder()
                .id(5L)
                .nombres("Juanito")
                .apellidos("Alcachofa")
                .numeroIdentificacion("188941981")
                .fotoMongoId("TODO")
                .ciudad(Ciudad.builder().id(1L).build())
                .tipoIdentificacion(TipoIdentificacion.builder().id(2L).build())
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
}
