package com.example.demo.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

@RestController
@CrossOrigin("*")
@RequestMapping("/health")
@Api(tags = "Health")
@Tag(name = "Health API", description = "Verifica el estado de la API")
public class HealthController {

    @GetMapping("/check")
    @Operation(summary = "Verifica el estado de la API")
    public ResponseEntity<String> checkAPI() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("<h1>The API is working ok!</h1>");
    }
}
