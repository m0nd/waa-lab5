package waa.labs.lab5.controllers;

import org.springframework.web.bind.annotation.*;
import waa.labs.lab5.dtos.CommentDto;
import waa.labs.lab5.dtos.request.PostRequestDto;
import waa.labs.lab5.dtos.response.PostResponseDto;
import waa.labs.lab5.services.IPostService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponseDto> getAllPosts(@RequestParam(name = "title", required = false) String postTitle) {
        return postTitle != null ? postService.getPostsWithTitleMatching(postTitle) : postService.getAllPosts();
    }


    @GetMapping("/{postId}")
    public PostResponseDto getPostById(@PathVariable long postId) {
        return postService.getPostById(postId);
    }


    @GetMapping("/{postId}/comments")
    public List<CommentDto> getAllPostComments(@PathVariable long postId) {
        return postService.getAllPostComments(postId);
    }

    @PostMapping
    public void savePost(@RequestBody PostRequestDto postDto, Principal principal) {
        postService.savePost(postDto, principal);
    }


    @PostMapping("/{postId}/comments")
    public void saveCommentForPost(@PathVariable long postId, @RequestBody CommentDto commentDto) {
        postService.saveCommentForPost(postId, commentDto);
    }


    @PutMapping("/{postId}")
    public void updatePost(@PathVariable long postId, @RequestBody PostRequestDto postDto) {
        postService.updatePost(postId, postDto);
    }


    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable long postId) {
        postService.deletePostById(postId);
    }
}
