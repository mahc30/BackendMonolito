package backendJava.client.controller;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listCliente(){
        List<Cliente> clients = clienteService.listAllCliente();
        if(clients.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(clients);
    }

    @GetMapping(value="/{tipoIdentificacionId}/{numeroIdentificacion}")
    public ResponseEntity<Cliente> getCliente(@PathVariable("tipoIdentificacionId") Long tipoIdentificacionId,@PathVariable("numeroIdentificacion") String numeroIdentificacion){
        if(tipoIdentificacionId == null
        || numeroIdentificacion == null) return ResponseEntity.badRequest().build();

        Cliente client = clienteService.findByTipoIdentificacionAndNumeroIdentificacion(
                TipoIdentificacion.builder().id(tipoIdentificacionId).build(),
                numeroIdentificacion );

        if(client == null) return ResponseEntity.noContent().build();
        return  ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente client){
        Cliente createdClient = clienteService.createCliente(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente){
        cliente.setId(id);
        Cliente clienteDB = clienteService.updateCliente(cliente);

        if(clienteDB == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(clienteDB);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable("id") Long id){
        clienteService.deleteCliente(id);
        return  ResponseEntity.ok().build();
    }
}
