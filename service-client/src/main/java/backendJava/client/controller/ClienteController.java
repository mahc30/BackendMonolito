package backendJava.client.controller;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.exception.Cliente.ClienteAlreadyExistsException;
import backendJava.client.exception.Cliente.ClienteNotFoundException;
import backendJava.client.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listCliente(){
        List<Cliente> clients = clienteService.listAllCliente();
        return ResponseEntity.ok(clients);
    }

    @GetMapping(value="/{edad}")
    public ResponseEntity<List<Cliente>> filterClienteByEdad(@PathVariable("edad") int edad){
        List<Cliente> clients = clienteService.findByEdadGreaterThan(edad);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(value="/{tipoIdentificacionId}/{numeroIdentificacion}")
    public ResponseEntity<Cliente> getCliente(@PathVariable("tipoIdentificacionId") TipoIdentificacion tipoIdentificacion,@PathVariable("numeroIdentificacion") String numeroIdentificacion){

        Cliente client = clienteService.findByTipoIdentificacionAndNumeroIdentificacion(
                tipoIdentificacion,
                numeroIdentificacion );

        if(client == null) throw new ClienteNotFoundException(tipoIdentificacion, numeroIdentificacion);
        return  ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente( @RequestBody Cliente client){

        Cliente clienteDB = clienteService.findByTipoIdentificacionAndNumeroIdentificacion(client.getTipoIdentificacion(), client.getNumeroIdentificacion());
        if(clienteDB != null) throw new ClienteAlreadyExistsException(clienteDB.getTipoIdentificacion(), clienteDB.getNumeroIdentificacion());

        clienteDB = clienteService.createCliente(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDB);
    }

    @PutMapping(value="/{tipoIdentificacionId}/{numeroIdentificacion}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente client, @PathVariable("tipoIdentificacionId") TipoIdentificacion tipoIdentificacion,@PathVariable("numeroIdentificacion") String numeroIdentificacion){
        Cliente clienteDB = clienteService.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion);
        if(clienteDB == null) throw new ClienteNotFoundException(client.getTipoIdentificacion(), client.getNumeroIdentificacion());

        client.setId(clienteDB.getId());
        clienteDB = clienteService.updateCliente(client);

        return ResponseEntity.ok(clienteDB);
    }

    @DeleteMapping(value="/{tipoIdentificacionId}/{numeroIdentificacion}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable("tipoIdentificacionId") TipoIdentificacion tipoIdentificacion,@PathVariable("numeroIdentificacion") String numeroIdentificacion){
        Cliente client = clienteService.findByTipoIdentificacionAndNumeroIdentificacion(
                tipoIdentificacion,
                numeroIdentificacion);

        if(client == null) throw new ClienteNotFoundException(tipoIdentificacion, numeroIdentificacion);

        clienteService.deleteCliente(client.getId());
        return  ResponseEntity.ok().build();
    }
}
