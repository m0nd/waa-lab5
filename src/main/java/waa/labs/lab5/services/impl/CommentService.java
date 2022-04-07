package waa.labs.lab5.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import waa.labs.lab5.dtos.CommentDto;
import waa.labs.lab5.entities.Comment;
import waa.labs.lab5.repositories.ICommentRepo;
import waa.labs.lab5.services.ICommentService;
import waa.labs.lab5.utils.ListMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentService implements ICommentService {
    ICommentRepo commentRepo;
    ModelMapper modelMapper;
    ListMapper<Comment, CommentDto> commentListMapperToDto;

    public CommentService(
            ICommentRepo commentRepo,
            ModelMapper modelMapper,
            ListMapper<Comment, CommentDto> commentListMapper
    ) {
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
        this.commentListMapperToDto = commentListMapper;
    }

    @Override
    public List<CommentDto> getAllComments() {
        return commentListMapperToDto.mapList(commentRepo.findAll(), CommentDto.class);
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        return modelMapper.map(commentRepo.findById(commentId).orElse(null), CommentDto.class);
    }
}
