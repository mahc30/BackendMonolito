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
        return ResponseEntity.ok(fotoService.listAllFoto());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Foto> getFoto(@PathVariable("id") String id){
        return  ResponseEntity.ok(fotoService.getFoto(id));
    }

    @PostMapping(value = "/{tipoIdentificacion}/{NumeroIdentificacion}")
    public ResponseEntity createFoto(@PathVariable("tipoIdentificacion") TipoIdentificacion tipoId, @PathVariable("NumeroIdentificacion") String numeroId, @RequestPart MultipartFile file){
        return ResponseEntity.ok(fotoService.createFoto(tipoId, numeroId, file));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity updateFoto(@PathVariable("id") String id, @RequestPart MultipartFile file){
        return ResponseEntity.ok(fotoService.updateFoto(id, file));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Foto> deleteCliente(@Valid @PathVariable("id") String id){
        fotoService.deleteFoto(id);
        return  ResponseEntity.ok().build();
    }
}
