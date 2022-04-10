package waa.labs.lab5.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waa.labs.lab5.dtos.CommentDto;
import waa.labs.lab5.dtos.request.PostRequestDto;
import waa.labs.lab5.dtos.response.PostResponseDto;
import waa.labs.lab5.entities.Comment;
import waa.labs.lab5.entities.Post;
import waa.labs.lab5.repositories.IPostRepo;
import waa.labs.lab5.repositories.IUserRepo;
import waa.labs.lab5.services.IPostService;
import waa.labs.lab5.utils.ListMapper;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService implements IPostService {
    IPostRepo postRepo;
    IUserRepo userRepo;

    ModelMapper modelMapper;
    ListMapper<Post, PostResponseDto> postListToDtoMapper;
    ListMapper<Comment, CommentDto> commentListToDtoMapper;

    @Autowired
    public PostService(
            IPostRepo postRepo,
            IUserRepo userRepo,
            ModelMapper modelMapper,
            ListMapper<Post, PostResponseDto> postListMapper,
            ListMapper<Comment, CommentDto> commentListMapper
    ) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.postListToDtoMapper = postListMapper;
        this.commentListToDtoMapper = commentListMapper;
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postListToDtoMapper.mapList(postRepo.findAll(), PostResponseDto.class);
    }

    @Override
    public PostResponseDto getPostById(long postId) {
        var desiredPost = postRepo.findById(postId).orElse(null);
        return (desiredPost == null) ? null : modelMapper.map(desiredPost, PostResponseDto.class);
    }

    @Override
    public List<PostResponseDto> getPostsWithTitleMatching(String postTitle) {
        return postListToDtoMapper.mapList(postRepo.findByTitleStartsWith(postTitle), PostResponseDto.class);
    }

    @Override
    public List<CommentDto> getAllPostComments(long postId) {
        var desiredPost = postRepo.findById(postId).orElse(null);
        if (desiredPost == null) return new ArrayList<>();
        return commentListToDtoMapper.mapList(desiredPost.getComments(), CommentDto.class);
    }

    @Override
    public void savePost(PostRequestDto postDto, Principal principal) {
        var desiredUser = userRepo.findByEmail(principal.getName());
        if (desiredUser != null) {
            Post newPost = new Post();
            newPost.setTitle(postDto.getTitle());
            newPost.setContent(postDto.getContent());
            desiredUser.addPost(newPost);
            userRepo.save(desiredUser);
        }
    }

    @Override
    public void saveCommentForPost(long postId, CommentDto commentDto) {
        Post desiredPost = postRepo.findById(postId).orElse(null);
        if (desiredPost != null) {
            Comment newComment = new Comment();
            newComment.setName(commentDto.getName());
            desiredPost.addComment(newComment);
            postRepo.save(desiredPost);
        }
    }

    @Override
    public void updatePost(long postId, PostRequestDto postDto) {
        Post desiredPost = postRepo.findById(postId).orElse(null);
        if (desiredPost != null) {
            desiredPost.setTitle(postDto.getTitle());
            desiredPost.setContent(postDto.getContent());
            postRepo.save(desiredPost);
        }
    }

    @Override
    public void deletePostById(long postId) {
        postRepo.deleteById(postId);
    }
}
