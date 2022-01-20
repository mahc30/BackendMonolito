package backendJava.client.controller;

import backendJava.client.entity.Foto;
import backendJava.client.service.FotoService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Foto> getFoto(@PathVariable("id") String id){
        Foto found = fotoService.getFoto(id);

        if(found == null) return ResponseEntity.noContent().build();
        return  ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity createFoto(@RequestPart MultipartFile file){

        Foto foto = Foto.builder().build();

        try{
            foto.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo convertir la imagen");
        }

        Foto createdFoto = fotoService.createFoto(foto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoto);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Foto> updateFoto(@PathVariable("id") String id, @ModelAttribute Foto foto){
        foto.setId(id);
        Foto fotoDB = fotoService.updateFoto(foto);

        if(fotoDB == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(fotoDB);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Foto> deleteCliente(@PathVariable("id") String id){
        if(fotoService.getFoto(id) == null) return ResponseEntity.notFound().build();
        fotoService.deleteFoto(id);

        if(fotoService.getFoto(id) != null) return ResponseEntity.internalServerError().build();
        return  ResponseEntity.ok().build();
    }
}
