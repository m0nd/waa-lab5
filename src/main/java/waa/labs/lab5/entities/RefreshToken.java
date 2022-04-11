package waa.labs.lab5.entities;

import javax.persistence.*;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body;

    @OneToOne
    private User associatedUser;

    public RefreshToken() {}

    public RefreshToken(String body) {
        this.body = body;
    }
}
