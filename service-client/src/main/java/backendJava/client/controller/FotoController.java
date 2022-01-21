package backendJava.client.controller;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.Foto;
import backendJava.client.exception.Foto.FotoDeleteErrorException;
import backendJava.client.exception.Foto.FotoFileConversionErrorException;
import backendJava.client.exception.Foto.FotoNotFoundException;
import backendJava.client.service.FotoService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping(value = "/fotos", consumes = MediaType.ALL_VALUE)
public class FotoController {
    @Autowired
    private FotoService fotoService;

    @GetMapping
    public ResponseEntity<List<Foto>> listFoto(){
        List<Foto> fotos = fotoService.listAllFoto();
        if(fotos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(fotos);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Foto> getFoto(@Valid @PathVariable("id") String id){
        Foto found = fotoService.getFoto(id);

        if(found == null) throw new FotoNotFoundException(id);
        return  ResponseEntity.ok(found);
    }

    @PostMapping(value = "/{clientIdType}/{clientIdNumber}")
    public ResponseEntity createFoto(@Valid @PathVariable("clientIdType") Long clientId, @PathVariable("clientIdNumber") String idNumber, @RequestPart MultipartFile file){

        Foto foto = Foto.builder().build();

        try{
            foto.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        }catch (Exception e){
            throw new FotoFileConversionErrorException();
        }

        Foto createdFoto = fotoService.createFoto(foto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoto);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity updateFoto(@Valid @PathVariable("id") String id, @RequestPart MultipartFile file){
        Foto foto = fotoService.getFoto(id);
        if(foto == null) throw new FotoNotFoundException(id);

        try{
            foto.setFile(Foto.convertMultipartToBinary(file));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo convertir la imagen");
        }

        foto = fotoService.updateFoto(foto);

        if(foto == null) return ResponseEntity.notFound().build();
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
