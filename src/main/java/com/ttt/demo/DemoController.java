package com.ttt.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    // ─────────────────────────────────────────
    // 1. GET đơn giản - trả về String
    // GET http://localhost:8080/api/hello
    // ─────────────────────────────────────────
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    // ─────────────────────────────────────────
    // 2. GET với Path Variable
    // GET http://localhost:8080/api/hello/Nam
    // ─────────────────────────────────────────
    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) {
        return "Xin chào, " + name + "!";
    }

    // ─────────────────────────────────────────
    // 3. GET trả về List object (dùng Lombok)
    // GET http://localhost:8080/api/users
    // ─────────────────────────────────────────
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = List.of(
            User.builder().id(1).name("Alice").email("alice@example.com").age(25).build(),
            User.builder().id(2).name("Bob").email("bob@example.com").age(30).build(),
            User.builder().id(3).name("Charlie").email("charlie@example.com").age(22).build()
        );
        return ResponseEntity.ok(users);
    }

    // ─────────────────────────────────────────
    // 4. GET với Query Param
    // GET http://localhost:8080/api/search?keyword=spring
    // ─────────────────────────────────────────
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(Map.of(
            "keyword", keyword,
            "results", List.of("Spring Boot", "Spring Web", "Spring Security"),
            "total", 3
        ));
    }

    // ─────────────────────────────────────────
    // 5. POST nhận JSON body
    // POST http://localhost:8080/api/users
    // Body: { "name": "Dave", "email": "dave@example.com", "age": 28 }
    // ─────────────────────────────────────────
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        user.setId(99); // Giả lập ID được tạo
        return ResponseEntity
            .status(201)
            .body(ApiResponse.<User>builder()
                .success(true)
                .message("Tạo user thành công!")
                .data(user)
                .timestamp(LocalDateTime.now())
                .build());
    }

    // ─────────────────────────────────────────
    // 6. PUT cập nhật theo ID
    // PUT http://localhost:8080/api/users/1
    // Body: { "name": "Alice Updated", "email": "alice@new.com", "age": 26 }
    // ─────────────────────────────────────────
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok(ApiResponse.<User>builder()
            .success(true)
            .message("Cập nhật user #" + id + " thành công!")
            .data(user)
            .timestamp(LocalDateTime.now())
            .build());
    }

    // ─────────────────────────────────────────
    // 7. DELETE theo ID
    // DELETE http://localhost:8080/api/users/1
    // ─────────────────────────────────────────
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok(ApiResponse.<Void>builder()
            .success(true)
            .message("Đã xóa user #" + id)
            .timestamp(LocalDateTime.now())
            .build());
    }


    // ═══════════════════════════════════════════
    // Inner classes (Lombok)
    // ═══════════════════════════════════════════

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private int id;
        private String name;
        private String email;
        private int age;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private LocalDateTime timestamp;
    }
}