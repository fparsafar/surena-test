package ir.surena.sample.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class CreateUserDTO {


    private String firstName;

    private String lastName;

    @Pattern(regexp = "^[^<>%\\-@+$|='\"]*$")
    private String username;


    private String password;


}
