package org.firefrogs.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
public class UserParameters {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private Double weight;
    @Column(nullable = false)
    private Integer height;
    @Transient
    private Integer age;

    public Integer getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
