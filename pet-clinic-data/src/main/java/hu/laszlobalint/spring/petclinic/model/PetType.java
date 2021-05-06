package hu.laszlobalint.spring.petclinic.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "pet_type")
public class PetType extends BaseEntity {

    @Builder
    public PetType(String name) {
        this.name = name;
    }

    @Column(name = "name")
    private String name;
}