package ir.surena.sample.serviceImpl;

import ir.surena.sample.domain.jpa.User;
import ir.surena.sample.dto.ChangePasswordDTO;
import ir.surena.sample.dto.CreateUserDTO;
import ir.surena.sample.dto.UpdateDTO;
import ir.surena.sample.dto.UserDTO;
import ir.surena.sample.exception.NotValidRequestException;
import ir.surena.sample.exception.SenderException;
import ir.surena.sample.exception.UserNotFoundException;
import ir.surena.sample.mapper.CreateDTOMapper;
import ir.surena.sample.mapper.UserDTOMapper;
import ir.surena.sample.repository.jpa.UserRepository;
import ir.surena.sample.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final CreateDTOMapper createDTOMapper;


    @Override
    public Page<User> getAll(Integer page, Integer size) {

        log.debug("try to fetch user from database : '{}' , page : '{}' , size : '{}'", page, size);

        var response = userRepository.findAll(PageRequest.of(page, size));

        log.debug("successfully fetch user from database  page : '{}' , size : '{}' , totalSize : '{}'", page, size, response.getTotalElements());

        return response;

    }

    @Override
    public UserDTO getByUserName(String username) throws SenderException {

        log.debug("try to found user by username. username : '{}'", username);

        var user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        log.debug("successfully found user by username.  user : '{}'", user);

        return userDTOMapper.convert(user);

    }

    @Override
    public void createUser(CreateUserDTO createUserDTO) {

        log.debug("try to save user in database. username : '{}'", createUserDTO.getUsername());

        Base64 base64 = new Base64();
        String encodedPassword = new String(base64.encode(createUserDTO.getPassword().getBytes()));
        createUserDTO.setPassword(encodedPassword);

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(createUserDTO.getPassword());
//        createUserDTO.setPassword(encodedPassword);
//
//
        var saveUser = userRepository.save(createDTOMapper.convertToEntity(createUserDTO));

        log.debug("successfully save user in database. user : '{}'", saveUser);

    }

    @Override
    public void updateUser(UpdateDTO updateDTO) throws SenderException {

        log.info("try to update updateDTO : '{}'", updateDTO);

        var user = userRepository.findByExternalId(updateDTO.getExternalId());

        if (user.isPresent()) {
            var updated = user.get();
            if (Objects.nonNull(updateDTO.getFirstName()) && updateDTO.getFirstName() != "") {
                updated.setFirstName(updateDTO.getFirstName());
            }
            if (Objects.nonNull(updateDTO.getLastName()) && updateDTO.getLastName() != "") {
                updated.setFirstName(updateDTO.getFirstName());
            }
//            if((Objects.nonNull(updateDTO.getFirstName()) && updateDTO.getFirstName()==updated.getFirstName()) ||
//                    (Objects.nonNull(updateDTO.getLastName()) && updateDTO.getLastName()==updated.getFirstName())
//            ) {
////                means that we do not any change !
//                return;
//            }
            try {

                userRepository.save(updated);

            } catch (DataIntegrityViolationException e) {
                throw new NotValidRequestException("{Duplicate_Record}");
            } catch (Exception e) {
                throw e;
            }

        }

        log.info("successfully updated the user with externalId  : '{}' ", updateDTO.getExternalId());

    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws SenderException{

        log.info("try to update password. userId : '{}', password : '*****' ", changePasswordDTO.getUsername());

        var user = userRepository.findByUsername(changePasswordDTO.getUsername()).orElseThrow(UserNotFoundException::new);;


            Base64 base64 = new Base64();
            String encodedPassword = new String(base64.encode(changePasswordDTO.getOldPassword().getBytes()));

            String decodedString = new String(base64.decode(user.getPassword().getBytes()));

//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String encodedPassword = passwordEncoder.encode(changePasswordDTO.getOldPassword());
            if (user.getPassword().equals(encodedPassword)) {
                String newPasswordEncoded = new String(base64.encode(changePasswordDTO.getPasswordNew().getBytes()));
                user.setPassword(newPasswordEncoded);


        }

        userRepository.save(user);

        log.info("successfully update password. username : '{}', password : '*****' , realmName : '{}'", changePasswordDTO.getUsername());


    }

    @Override
    public void removeById(Long id) throws SenderException {

        log.info("try to remove user . id : '{}'", id);

        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);


        this.userRepository.delete(user);
        log.debug(" remove user from db id : '{}'", id);


    }

    @Override
    public void removeByExternalId(String externalId) {

        log.info("try to remove user . externalId : '{}'", externalId);

        var user = userRepository.findByExternalId(externalId);


        if (user.isPresent()) {

            this.userRepository.delete(user.get());
            log.debug(" remove user from db externalId : '{}'", externalId);

        }

    }

    @Override
    public void removeByUsername(String username) throws SenderException {

        log.info("try to remove user . username : '{}'", username);

        var user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);


        this.userRepository.delete(user);
        log.debug(" remove user from db username : '{}'", username);


    }

}
