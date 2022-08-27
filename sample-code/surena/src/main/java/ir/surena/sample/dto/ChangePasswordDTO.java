package ir.surena.sample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"password", "passwordNew", "passwordConfirm"})
public class ChangePasswordDTO {

    @NotEmpty
    @Pattern(regexp = "^[^<>%\\-@+$|='\"]*$")
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordNew;

    @NotEmpty
    private String oldPassword;
}
