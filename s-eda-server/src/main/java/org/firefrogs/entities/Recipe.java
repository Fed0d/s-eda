package org.firefrogs.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column(nullable = false)
    private String cookingTime;
    @Column(nullable = false)
    private String additionalTime;
    private String photoLink;
    @ManyToOne
    @JoinColumn(name = "dish_type_id", nullable = false)
    private DishType dishType;
    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> ingredients;
}
