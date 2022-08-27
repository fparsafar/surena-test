package ir.surena.sample.service;

import ir.surena.sample.domain.jpa.User;
import ir.surena.sample.dto.ChangePasswordDTO;
import ir.surena.sample.dto.CreateUserDTO;
import ir.surena.sample.dto.UpdateDTO;
import ir.surena.sample.dto.UserDTO;
import ir.surena.sample.exception.NotValidRequestException;
import ir.surena.sample.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

public interface UserService {


    Page<User> getAll(Integer page, Integer size);

    UserDTO getByUserName(String username) throws UserNotFoundException;

    void createUser(CreateUserDTO createUserDTO);

    void updateUser(UpdateDTO updateDTO) throws NotValidRequestException;

    void changePassword(ChangePasswordDTO changePasswordDTO);

    void removeById(Long id);

    void removeByExternalId(String externalId);

    void removeByUsername(String username);

}
