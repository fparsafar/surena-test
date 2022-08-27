package ir.surena.sample.mapper;

import ir.surena.sample.domain.jpa.User;
import ir.surena.sample.dto.UserDTO;
import ir.surena.sample.util.GeneralBase;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {



    @Mapping(source = "createDateTime", target = "shamsiCreateDateTime", qualifiedByName = "convertDateToShamsi")
    UserDTO convert(User user);

    User convertToEntity(UserDTO userDTO);


    @Named("convertDateToShamsi")
    default String convertDateToShamsi(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return null;
        }

        var fullSahmsiDate = GeneralBase.convertLocalDateToShamsi(localDateTime).split("\\s");

        return fullSahmsiDate[0];
    }
}
