package org.firefrogs.repositories;

import org.firefrogs.entities.DishType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishTypeRepository extends JpaRepository<DishType, Long> {
    DishType findByName(String name);

}
