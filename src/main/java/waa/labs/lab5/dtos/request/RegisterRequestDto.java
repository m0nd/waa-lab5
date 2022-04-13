package waa.labs.lab5.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;

    private List<String> roles;
}
