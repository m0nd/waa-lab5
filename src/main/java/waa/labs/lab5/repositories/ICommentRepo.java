package waa.labs.lab5.repositories;

import org.springframework.data.repository.CrudRepository;
import waa.labs.lab5.entities.Comment;

import java.util.List;

public interface ICommentRepo extends CrudRepository<Comment, Long> {
    @Override
    List<Comment> findAll();
}
