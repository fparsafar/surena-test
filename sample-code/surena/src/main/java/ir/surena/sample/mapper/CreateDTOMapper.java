package ir.surena.sample.mapper;


import ir.surena.sample.domain.jpa.User;
import ir.surena.sample.dto.CreateUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateDTOMapper {

    User convertToEntity(CreateUserDTO createUserDTO);



}
