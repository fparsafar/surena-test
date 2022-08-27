package ir.surena.sample.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateDTO {

    @NotEmpty
    private String externalId;

    private String firstName;

    private String lastName;

}
