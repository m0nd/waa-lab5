package waa.labs.lab5.dtos.request;

import lombok.Data;

@Data
public class PostRequestDto {
    long id;
    String title;
    String content;
    long authorId;
}
