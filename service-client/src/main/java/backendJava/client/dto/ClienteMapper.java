package backendJava.client.dto;

import backendJava.client.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(source="id", target="id")
    @Mapping(source="nombres", target="nombres")
    @Mapping(source="apellidos", target="apellidos")
    @Mapping(source="numeroIdentificacion", target="numeroIdentificacion")
    @Mapping(source="tipoIdentificacion", target="tipoIdentificacion")
    @Mapping(source="fotoMongoId", target="fotoMongoId")
    @Mapping(source="edad", target="edad")
    @Mapping(source="ciudad", target="ciudad")
    ClienteDTO clienteToClienteDto(Cliente cliente);

    @Mapping(source="id", target="id")
    @Mapping(source="nombres", target="nombres")
    @Mapping(source="apellidos", target="apellidos")
    @Mapping(source="numeroIdentificacion", target="numeroIdentificacion")
    @Mapping(source="tipoIdentificacion", target="tipoIdentificacion")
    @Mapping(source="fotoMongoId", target="fotoMongoId")
    @Mapping(source="edad", target="edad")
    @Mapping(source="ciudad", target="ciudad")
    Cliente clienteDtoToCliente(ClienteDTO clienteDto);

    List<ClienteDTO> mapEntityListToDtoList(List<Cliente> clientes);
    List<Cliente> mapDtoListToEntityList(List<ClienteDTO> clientes);

}
