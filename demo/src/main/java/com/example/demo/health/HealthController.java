package com.example.demo.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/health")
@Api(tags = "Health")
public class HealthController {

    @GetMapping("/check")
    @ApiOperation("Verifica el estado de la API")
    public ResponseEntity<String> checkAPI(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("<h1>The API is working ok!</h1>");
    }
}
