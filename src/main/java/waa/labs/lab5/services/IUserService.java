package waa.labs.lab5.services;

import waa.labs.lab5.dtos.CommentDto;
import waa.labs.lab5.dtos.UserDto;
import waa.labs.lab5.dtos.response.ResponsePostDto;

import java.util.List;

public interface IUserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(long userId);

    void saveUser(UserDto userDto);

    void savePostByUser(long userId, ResponsePostDto postDto);

    void saveCommentByUser(long userId, long postId, CommentDto commentDto);

    void updateUser(long userId, UserDto userDto);

    void deleteUserById(long userId);

    List<ResponsePostDto> getPostsByUser(long userId);

    List<UserDto> getUsersWithPostsMoreThan(int minNumPosts);

    List<UserDto> getUsersWithPostTitleMatching(String title);

    List<CommentDto> getAllUserComments(long userId, long postId);

    CommentDto getUserCommentById(long userId, long postId, long commentId);
}
