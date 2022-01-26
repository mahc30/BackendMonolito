package backendJava.client.controller;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.Foto;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.exception.Cliente.ClienteNotFoundException;
import backendJava.client.exception.Foto.FotoDeleteErrorException;
import backendJava.client.exception.Foto.FotoFileConversionErrorException;
import backendJava.client.exception.Foto.FotoNotFoundException;
import backendJava.client.service.ClienteService;
import backendJava.client.service.FotoService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping(value = "/fotos", consumes = MediaType.ALL_VALUE)
@Validated
public class FotoController {
    @Autowired
    private FotoService fotoService;

    @Autowired
    ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Foto>> listFoto(){
        List<Foto> fotos = fotoService.listAllFoto();
        if(fotos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(fotos);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Foto> getFoto(@PathVariable("id") String id){
        Foto found = fotoService.getFoto(id);

        if(found == null) throw new FotoNotFoundException(id);
        return  ResponseEntity.ok(found);
    }

    @PostMapping(value = "/{tipoIdentificacion}/{NumeroIdentificacion}")
    public ResponseEntity createFoto(@PathVariable("tipoIdentificacion") TipoIdentificacion tipoId, @PathVariable("NumeroIdentificacion") String numeroId, @RequestPart MultipartFile file){

        Cliente clienteDB = clienteService.findByTipoIdentificacionAndNumeroIdentificacion(tipoId, numeroId);
        if(clienteDB == null) throw new ClienteNotFoundException(tipoId, numeroId);

        Foto foto = Foto.builder().file(Foto.convertMultipartToBinary(file)).build();
        Foto createdFoto = fotoService.createFoto(foto);

        fotoService.deleteFoto(clienteDB.getFotoMongoId());
        clienteDB.setFotoMongoId(createdFoto.getId());
        clienteService.updateCliente(clienteDB);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoto);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity updateFoto(@PathVariable("id") String id, @RequestPart MultipartFile file){
        Foto foto = fotoService.getFoto(id);
        if(foto == null) throw new FotoNotFoundException(id);

        foto.setFile(Foto.convertMultipartToBinary(file));
        foto = fotoService.updateFoto(foto);

        return ResponseEntity.ok(foto);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Foto> deleteCliente(@Valid @PathVariable("id") String id){
        if(fotoService.getFoto(id) == null) throw new FotoNotFoundException(id);
        fotoService.deleteFoto(id);
        if(fotoService.getFoto(id) != null) throw new FotoDeleteErrorException(id);
        return  ResponseEntity.ok().build();
    }
}
