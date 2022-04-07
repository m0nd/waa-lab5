package waa.labs.lab5.services;

import waa.labs.lab5.dtos.CommentDto;

import java.util.List;

public interface ICommentService {
    List<CommentDto> getAllComments();

    CommentDto getCommentById(Long commentId);
}
