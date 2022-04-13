package waa.labs.lab5.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String body;

    @OneToOne
    private User associatedUser;

    public RefreshToken() {}

    public RefreshToken(String body) {
        this.body = body;
    }
}
