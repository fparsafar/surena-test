package ir.surena.sample.domain.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity
@Table(name = "user_tbl", indexes = {
        @Index(name = "idx_bufn", columnList = "first_name"),
        @Index(name = "idx_buln", columnList = "last_name"),
        @Index(name = "idx_buu", columnList = "username")
})
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends BaseEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "external_id", nullable = false, length = 36)
    private String externalId = UUID.randomUUID().toString();

    @NotEmpty
    @Column(name = "first_name", nullable = false, length = 36)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false, length = 36)
    private String lastName;

    @NotEmpty
    @Column(name = "username", nullable = false, length = 36, unique = true)
    @Pattern(regexp = "^[^<>%\\-@+$|='\"]*$")
    private String username;

    @NotEmpty
    @Column(name = "password", nullable = false, length = 36, unique = true)
    private String password;

}
