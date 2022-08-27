package ir.surena.sample.controller;


import ir.surena.sample.dto.ChangePasswordDTO;
import ir.surena.sample.dto.CreateUserDTO;
import ir.surena.sample.dto.UpdateDTO;
import ir.surena.sample.dto.UserDTO;
import ir.surena.sample.exception.NotValidRequestException;
import ir.surena.sample.exception.UserNotFoundException;
import ir.surena.sample.mapper.UserDTOMapper;
import ir.surena.sample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/user")
@Validated
@RequiredArgsConstructor
public class UserController {


    private final UserService userservice;
    private final UserDTOMapper userDTOMapper;


    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAll(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                                                @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(100) Integer size) {
        return ResponseEntity.ok(userservice.getAll(page, size).map(userDTOMapper::convert));
    }


    @GetMapping("/user/username}")
    public ResponseEntity<UserDTO> getByUsername(@PathVariable String username) throws UserNotFoundException {

        return ResponseEntity.ok(userservice.getByUserName(username));
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void createUser(@Valid @RequestBody CreateUserDTO request) {
        userservice.createUser(request);
    }

    @PostMapping("/change-password")
    @ResponseStatus(value = HttpStatus.OK)
    public void createUser(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        userservice.changePassword(changePasswordDTO);
    }


    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePost(@Valid @RequestBody UpdateDTO request) throws NotValidRequestException {
        userservice.updateUser(request);
    }


    @DeleteMapping("/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeById(@PathVariable Long id) {
        userservice.removeById(id);
    }


    @DeleteMapping("/user/{externalId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeByExternalId(@PathVariable String  externalId) {
        userservice.removeByExternalId(externalId);
    }



    @DeleteMapping("/user/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeByUsername(@PathVariable String username) {
        userservice.removeByUsername(username);
    }


}
