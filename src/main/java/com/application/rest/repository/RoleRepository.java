package com.application.rest.repository;

import com.application.rest.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,Long> {

    //querimetodo para que me devuelva el rol le envio el listado de string rolenames y solo me va a traer con los que existan en la BD.
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
