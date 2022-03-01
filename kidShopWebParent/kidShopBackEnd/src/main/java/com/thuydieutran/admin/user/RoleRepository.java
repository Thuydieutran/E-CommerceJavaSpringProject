package com.thuydieutran.admin.user;

import com.thuydieutran.kidshopcommon.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
