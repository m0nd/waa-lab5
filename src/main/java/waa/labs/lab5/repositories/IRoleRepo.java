package waa.labs.lab5.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.Role;
import waa.labs.lab5.utils.UserRole;

public interface IRoleRepo extends CrudRepository<Role, Integer> {
    Role findByRole(UserRole role);
}
