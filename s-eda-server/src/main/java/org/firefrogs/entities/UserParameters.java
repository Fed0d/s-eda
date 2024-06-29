package org.firefrogs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@RequiredArgsConstructor
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
