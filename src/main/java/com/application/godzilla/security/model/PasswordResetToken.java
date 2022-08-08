package com.application.godzilla.security.model;

import com.application.godzilla.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "password_reset_token")
public class PasswordResetToken {

    private static final int EXPIRATION = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prt_seq")
    @SequenceGenerator(name = "prt_seq", allocationSize = 1, sequenceName = "prt_seq")
    @Column(name = "prt_id")
    private Long id;

    @Column(name = "prt_token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "prt_usr_id")
    private User user;

    @Column(name = "prt_expiryDate")
    private Date expiryDate;

    public PasswordResetToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expiryDate = new Date(System.currentTimeMillis() + EXPIRATION * 60000);
    }
}