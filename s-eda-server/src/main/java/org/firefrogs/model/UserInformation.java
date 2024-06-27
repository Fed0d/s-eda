package org.firefrogs.model;

import jakarta.persistence.*;

@Entity
public class UserInformation {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
