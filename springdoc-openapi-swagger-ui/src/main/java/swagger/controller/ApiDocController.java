package swagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import swagger.model.Product;

import java.util.List;

// TODO. 使用Swagger注解来丰富API的说明
@RestController
public class ApiDocController {

    @Operation(summary = "GET", description = "View list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "401", description = "Auth failed to operate"),
            @ApiResponse(responseCode = "500", description = "Internal Sever Issue")
    })
    @GetMapping("/users")
    public ResponseEntity<List<String>> listUsers() {
        List<String> response = List.of("User1", "User2", "User3");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "POST", description = "New User Creation")
    @PostMapping("/user/creation")
    public ResponseEntity<String> createUser(String user) {
        System.out.println(user);
        return new ResponseEntity<>("Create OK", HttpStatus.OK);
    }

    // API Doc会自动创建返回类型的描述
    @Operation(summary = "GET", description = "Fetch Product Information")
    @GetMapping("/product")
    public ResponseEntity<Product> fetchProduct() {
         Product product = new Product(1, "apple", "detail of product");
         return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
