package com.ruoyi.website.controller.test;



import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class Test {
    @GetMapping("/testController")
    public ResponseEntity<String> testController() {

        return ResponseEntity.ok("ok");
    }
}
