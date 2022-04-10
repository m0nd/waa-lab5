package waa.labs.lab5.services;

import waa.labs.lab5.dtos.CommentDto;
import waa.labs.lab5.dtos.request.PostRequestDto;
import waa.labs.lab5.dtos.response.PostResponseDto;

import java.security.Principal;
import java.util.List;

public interface IPostService {
    List<PostResponseDto> getAllPosts();

    PostResponseDto getPostById(long postId);

    List<PostResponseDto> getPostsWithTitleMatching(String postTitle);

    List<CommentDto> getAllPostComments(long postId);

    void savePost(PostRequestDto postDto, Principal principal);

    void saveCommentForPost(long postId, CommentDto commentDto);

    void updatePost(long postId, PostRequestDto postDto);

    void deletePostById(long postId);
}
