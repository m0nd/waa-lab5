package waa.labs.lab5.dtos.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponseDto {
    private int code;
    private String status;
    private String message;

    public ErrorResponseDto() {}

    public ErrorResponseDto(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }
}
