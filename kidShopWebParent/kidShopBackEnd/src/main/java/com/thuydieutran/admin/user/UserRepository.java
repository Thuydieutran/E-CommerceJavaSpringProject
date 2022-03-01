package com.thuydieutran.admin.user;


import com.thuydieutran.kidshopcommon.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
