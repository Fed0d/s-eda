package org.firefrogs.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.firefrogs.dto.MenuResponse;
import org.firefrogs.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menu")
@AllArgsConstructor
@Tag(name = "Меню")
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "Получение меню на 1 день")
    @GetMapping
    public ResponseEntity<MenuResponse> getMenu() {
        return ResponseEntity.ok(menuService.getMenu());
    }
}
