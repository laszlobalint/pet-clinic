package hu.laszlobalint.spring.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pet_type")
public class PetType extends BaseEntity {

    @Builder
    public PetType(String name) {
        this.name = name;
    }

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}