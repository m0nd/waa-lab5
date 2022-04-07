package waa.labs.lab5.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.Post;

import java.util.List;

public interface IPostRepo extends CrudRepository<Post, Long> {
    @Override
    List<Post> findAll();

    @Query("from Post p where p.title like :postTitle%")
    List<Post> findByTitleStartsWith(String postTitle);
}
