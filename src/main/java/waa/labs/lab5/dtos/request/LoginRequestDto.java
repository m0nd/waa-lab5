package waa.labs.lab5.dtos.request;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
