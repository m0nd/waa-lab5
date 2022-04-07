package waa.labs.lab5.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waa.labs.lab5.dtos.CommentDto;
import waa.labs.lab5.dtos.UserDto;
import waa.labs.lab5.dtos.response.ResponsePostDto;
import waa.labs.lab5.entities.Comment;
import waa.labs.lab5.entities.Post;
import waa.labs.lab5.entities.User;
import waa.labs.lab5.repositories.IPostRepo;
import waa.labs.lab5.repositories.IUserRepo;
import waa.labs.lab5.services.IUserService;
import waa.labs.lab5.utils.ListMapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService implements IUserService {
    IUserRepo userRepo;
    IPostRepo postRepo;

    private final ModelMapper modelMapper;

    private final ListMapper<User, UserDto> userListToDtoMapper;
    private final ListMapper<Post, ResponsePostDto> postListToDtoMapper;
    private final ListMapper<Comment, CommentDto> commentListToDtoMapper;

    @Autowired
    UserService(
            IUserRepo userRepo,
            IPostRepo postRepo,
            ModelMapper modelMapper,
            ListMapper<User, UserDto> userListMapper,
            ListMapper<Post, ResponsePostDto> postListMapper,
            ListMapper<Comment, CommentDto> commentListMapper
    ) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
        this.userListToDtoMapper = userListMapper;
        this.postListToDtoMapper = postListMapper;
        this.commentListToDtoMapper = commentListMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userListToDtoMapper.mapList(userRepo.findAll(), UserDto.class);
    }

    @Override
    public UserDto getUserById(long userId) {
        return modelMapper.map(userRepo.findById(userId).orElse(null), UserDto.class);
    }

    @Override
    public void saveUser(UserDto userDto) {
        User newUser = new User();
        newUser.setName(userDto.getName());
        userRepo.save(newUser);
    }

    @Override
    public void savePostByUser(long userId, ResponsePostDto postDto) {
        User desiredUser = userRepo.findById(userId).orElse(null);
        if (desiredUser != null) {
            Post newPost = new Post();
            newPost.setContent(postDto.getContent());
            newPost.setTitle(postDto.getTitle());
            desiredUser.addPost(newPost);
        }
    }

    @Override
    public void saveCommentByUser(long userId, long postId, CommentDto commentDto) {
        var desiredUser = userRepo.findById(userId).orElse(null);
        if (desiredUser == null) return;

        var desiredPost = desiredUser.getPosts().stream().filter(post -> post.getId() == postId).findFirst().orElse(null);
        if (desiredPost == null) return;

        Comment newComment = new Comment();
        newComment.setName(commentDto.getName());
        desiredPost.addComment(newComment);
    }

    @Override
    public void updateUser(long userId, UserDto userDto) {
        var userToUpdate = userRepo.findById(userId).orElse(null);

        if (userToUpdate != null) {
            userToUpdate.setName(userDto.getName());
            userRepo.save(userToUpdate);
        }
    }

    @Override
    public void deleteUserById(long userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public List<ResponsePostDto> getPostsByUser(long userId) {
        var user = userRepo.findById(userId).orElse(null);
        if (user == null)
            return new ArrayList<>();
        return postListToDtoMapper.mapList(user.getPosts(), ResponsePostDto.class);
    }

    @Override
    public List<UserDto> getUsersWithPostsMoreThan(int minNumPosts) {
        return userListToDtoMapper.mapList(userRepo.findHavingPostsMoreThan(minNumPosts), UserDto.class);
    }

    @Override
    public List<UserDto> getUsersWithPostTitleMatching(String postTitle) {
        return userListToDtoMapper.mapList(userRepo.findHavingPostTitleMatching(postTitle), UserDto.class);
    }

    @Override
    public List<CommentDto> getAllUserComments(long userId, long postId) {
        var user = userRepo.findById(userId).orElse(null);
        if (user == null) return new ArrayList<>();

        var desiredPost = user.getPosts().stream().filter(post -> post.getId() == postId).findFirst().orElse(null);
        if (desiredPost == null) return new ArrayList<>();

        return commentListToDtoMapper.mapList(desiredPost.getComments(), CommentDto.class);
    }

    @Override
    public CommentDto getUserCommentById(long userId, long postId, long commentId) {
        var user = userRepo.findById(userId).orElse(null);
        if (user == null) return null;

        var desiredPost = user.getPosts().stream().filter(post -> post.getId() == postId).findFirst().orElse(null);
        if (desiredPost == null) return null;

        var desiredComment = desiredPost.getComments().stream().filter(comment -> comment.getId() == commentId).findFirst().orElse(null);

        return modelMapper.map(desiredComment, CommentDto.class);
    }
}
