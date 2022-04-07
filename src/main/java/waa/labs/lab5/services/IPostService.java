package waa.labs.lab5.services;

import waa.labs.lab5.dtos.CommentDto;
import waa.labs.lab5.dtos.request.RequestPostDto;
import waa.labs.lab5.dtos.response.ResponsePostDto;

import java.util.List;

public interface IPostService {
    List<ResponsePostDto> getAllPosts();

    ResponsePostDto getPostById(long postId);

    List<ResponsePostDto> getPostsWithTitleMatching(String postTitle);

    List<CommentDto> getAllPostComments(long postId);

    void savePost(RequestPostDto postDto);

    void saveCommentForPost(long postId, CommentDto commentDto);

    void updatePost(long postId, RequestPostDto postDto);

    void deletePostById(long postId);
}
