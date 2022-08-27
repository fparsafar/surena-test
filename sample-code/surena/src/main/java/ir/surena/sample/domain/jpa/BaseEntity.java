package ir.surena.sample.domain.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
@ToString
public class BaseEntity {

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    protected LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "modified_date")
    protected LocalDateTime updateDateTime;
}
