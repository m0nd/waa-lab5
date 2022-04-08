package waa.labs.lab5.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.User;

import java.util.List;

public interface IUserRepo extends CrudRepository<User, Long> {

    List<User> findAll();

    User findByEmail();

    @Query("select u from User u where size(u.posts) > :minNumPosts")
    List<User> findHavingPostsMoreThan(int minNumPosts);

    @Query("select u from User u join u.posts as p where p.title like :postTitle%")
    List<User> findHavingPostTitleMatching(String postTitle);
}
