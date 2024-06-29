package org.firefrogs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.firefrogs.DTO.IngredientDTO;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
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
    @Transient
    private List<IngredientDTO> ingredients;
}
