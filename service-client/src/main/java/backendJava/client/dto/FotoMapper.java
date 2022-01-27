package backendJava.client.dto;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.Foto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FotoMapper {
    FotoMapper INSTANCE = Mappers.getMapper(FotoMapper.class);

    @Mapping(source="id", target="id")
    @Mapping(source="file", target="file")
    FotoDTO fotoToFotoDto(Foto foto);

    @Mapping(source="id", target="id")
    @Mapping(source="file", target="file")
    Foto fotoDtoToFoto(FotoDTO foto);

    List<FotoDTO> mapEntityListToDtoList(List<Foto> clientes);
    List<Foto> mapDtoListToEntityList(List<FotoDTO> clientes);
}
