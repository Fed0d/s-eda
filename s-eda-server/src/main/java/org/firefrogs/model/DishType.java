package org.firefrogs.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dishes_types")
public class DishType {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
}
