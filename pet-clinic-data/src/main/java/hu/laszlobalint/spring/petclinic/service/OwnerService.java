package hu.laszlobalint.spring.petclinic.service;

import hu.laszlobalint.spring.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
