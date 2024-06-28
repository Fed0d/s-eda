package org.firefrogs.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Double calories;
    @Column(nullable = false)
    private Double proteins;
    @Column(nullable = false)
    private Double fats;
    @Column(nullable = false)
    private Double carbohydrates;
    @Column(nullable = false)
    private Boolean meat;
    @Column(nullable = false)
    private Boolean fish;
    @Column(nullable = false)
    private Boolean milk;
    @Column(nullable = false)
    private Boolean green;
    @Column(nullable = false)
    private Boolean gluten;
    @Column(nullable = false)
    private Boolean seafood;
    @Column(nullable = false)
    private Boolean flour;
    @Column(nullable = false)
    private Boolean mushroom;
    @Column(nullable = false)
    private Boolean halal;
    @Column(nullable = false)
    private Boolean vegan;
    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredient> recipes;
}
