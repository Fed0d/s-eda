package org.firefrogs.controllers;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.MenuDTO;
import org.firefrogs.services.impl.MenuServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menu")
@AllArgsConstructor
public class MenuController {
    private final MenuServiceImpl menuService;

    @GetMapping
    public ResponseEntity<MenuDTO> getMenu() {
        return ResponseEntity.ok(menuService.getMenu());
    }
}
