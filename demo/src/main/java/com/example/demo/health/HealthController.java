package com.example.demo.health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/check")
    public ResponseEntity<String> checkAPI(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("<h1>The API is working ok!</h1>");
   }
}