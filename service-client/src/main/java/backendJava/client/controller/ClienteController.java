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
        return ResponseEntity.ok(clienteService.listAllCliente());
    }

    @GetMapping(value="/{edad}")
    public ResponseEntity<List<Cliente>> filterClienteByEdad(@PathVariable("edad") int edad){
        return ResponseEntity.ok(clienteService.findByEdadGreaterThan(edad));
    }

    @GetMapping(value="/{tipoIdentificacionId}/{numeroIdentificacion}")
    public ResponseEntity<Cliente> getCliente(@PathVariable("tipoIdentificacionId") TipoIdentificacion tipoIdentificacion,@PathVariable("numeroIdentificacion") String numeroIdentificacion){
        return  ResponseEntity.ok(clienteService.findByTipoIdentificacionAndNumeroIdentificacion(
                tipoIdentificacion,
                numeroIdentificacion));
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente( @RequestBody Cliente client){
        return ResponseEntity.ok(clienteService.createCliente(client));
    }

    @PutMapping(value="/{tipoIdentificacionId}/{numeroIdentificacion}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente client, @PathVariable("tipoIdentificacionId") TipoIdentificacion tipoIdentificacion,@PathVariable("numeroIdentificacion") String numeroIdentificacion){
        return ResponseEntity.ok(clienteService.updateCliente(client));
    }

    @DeleteMapping(value="/{tipoIdentificacionId}/{numeroIdentificacion}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable("tipoIdentificacionId") TipoIdentificacion tipoIdentificacion,@PathVariable("numeroIdentificacion") String numeroIdentificacion){
        clienteService.deleteCliente(tipoIdentificacion, numeroIdentificacion);
        return  ResponseEntity.ok().build();
    }
}
