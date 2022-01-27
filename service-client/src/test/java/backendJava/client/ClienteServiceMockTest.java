package backendJava.client;

import backendJava.client.dto.ClienteDTO;
import backendJava.client.dto.ClienteMapper;
import backendJava.client.entity.Ciudad;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.repository.ClienteRepository;
import backendJava.client.repository.FotoRepository;
import backendJava.client.service.ClienteService;
import backendJava.client.service.ClienteServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceMockTest {


    private ClienteService clienteService;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private FotoRepository fotoRepository;

    private Cliente cliente1, cliente2;



    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository, fotoRepository);

        cliente1 = Cliente
                .builder()
                .id(1L)
                .nombres("MA")
                .apellidos("HC")
                .ciudad(Ciudad.Medellin)
                .fotoMongoId("")
                .tipoIdentificacion(TipoIdentificacion.CC)
                .numeroIdentificacion("100203403")
                .edad(21)
                .build();

        cliente2 = Cliente
                .builder()
                .id(1L)
                .nombres("Ho Chi")
                .apellidos("Minh")
                .ciudad(Ciudad.Bogota)
                .fotoMongoId("")
                .tipoIdentificacion(TipoIdentificacion.CE)
                .numeroIdentificacion("1976")
                .edad(79)
                .build();
    }

    @Test
    public void whenValidGetId_ThenReturnCliente(){
        when(clienteRepository.findById(1L)).thenReturn(Optional.ofNullable(cliente1));

        ClienteDTO clienteTest =  clienteService.getCliente(1L);

        verify(clienteRepository, times(1)).findById(1L);
        Assertions.assertThat(clienteTest.getApellidos()).isEqualTo(cliente1.getApellidos());
    }

    @Test
    public void whenValidCreateCliente_ThenReturnCliente(){
        when(clienteRepository.save(cliente1)).thenReturn(cliente1);

        ClienteDTO client = clienteService.createCliente(cliente1);

        verify(clienteRepository, times(1)).save(cliente1);
    }

    @Test
    public void whenValidUpdateCliente_ThenReturnUpdatedCliente(){
        when(clienteRepository.findById(1L)).thenReturn(Optional.ofNullable(cliente1));
        when(clienteRepository.save(cliente1)).thenReturn(cliente1);
        when(clienteRepository.save(cliente2)).thenReturn(cliente2);

        ClienteDTO clienteTest = clienteService.updateCliente(cliente1);

        verify(clienteRepository, times(1)).save(cliente1);
        Assertions.assertThat(clienteTest.getEdad()).isEqualTo(cliente1.getEdad());

        clienteTest = clienteService.updateCliente(cliente2);

        verify(clienteRepository, times(2)).save(cliente2);
        Assertions.assertThat(clienteTest.getApellidos()).isEqualTo(cliente2.getApellidos());

    }

    @Test
    public void whenValidDeleteCliente_ThenReturnVerifyMethodIsCalled(){
        when(clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion.CC, "100203403")).thenReturn(cliente1);

        clienteService.deleteCliente(cliente1.getTipoIdentificacion(),cliente1.getNumeroIdentificacion());
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void whenValidfindByTipoIdentificacionAndNumeroIdentificacion_ThenReturnClient(){
        when(clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion.CC, "100203403")).thenReturn(cliente1);

        ClienteDTO clienteTest = clienteService.findByTipoIdentificacionAndNumeroIdentificacion(cliente1.getTipoIdentificacion(), cliente1.getNumeroIdentificacion());

        verify(clienteRepository, times(1)).findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion.CC, "100203403");
        Assertions.assertThat(clienteTest.getEdad()).isEqualTo(cliente1.getEdad());
    }

    @Test
    public void whenValidFindByEdadGreaterThan_ThenReturnClientList(){
        List<Cliente> mockReturnList = Arrays.asList(new Cliente[]{cliente1, cliente2});

        doReturn(mockReturnList).when(clienteRepository).findByEdadGreaterThanEqual(21);

        List<ClienteDTO> founds = clienteService.findByEdadGreaterThanEqual(21);

        Assertions.assertThat(founds.size()).isEqualTo(2);

    }

    @Test
    public void whenFindByEdadGreaterThanAnyClientAge_ThenReturnEmptyList(){
        when(clienteRepository.findByEdadGreaterThanEqual(70)).thenReturn(Arrays.asList(new Cliente[]{cliente2}));

        List<ClienteDTO> founds = clienteService.findByEdadGreaterThanEqual(70);

        Assertions.assertThat(founds.size()).isEqualTo(1);
        Assertions.assertThat(founds.get(0).getNombres()).isEqualTo(cliente2.getNombres());
    }
}
