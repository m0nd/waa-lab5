package waa.labs.lab5.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.Role;

public interface IRoleRepo extends CrudRepository<Role, Integer> {
    Role findByName(String roleName);
}
