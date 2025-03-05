package swagger;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO. 使用Swagger注解来丰富API的说明
@RestController
public class CustomerController {

    @ApiOperation(value = "View list of users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "Auth failed to operate"),
            @ApiResponse(code = 500, message = "Internal Sever Issue")
    })
    @GetMapping("/users")
    public ResponseEntity<List<String>> listUsers() {
        List<String> response = List.of("User1", "User2", "User3");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "New User creation")
    @GetMapping("/user/creation")
    public ResponseEntity<String> createUser(String user) {
        System.out.println(user);
        return new ResponseEntity<>("Create OK", HttpStatus.OK);
    }

    @ApiOperation(value = "Old User deletion")
    @GetMapping("/user/deletion")
    public ResponseEntity<String> deleteUser(String user) {
        System.out.println(user);
        return new ResponseEntity<>("Delete OK", HttpStatus.OK);
    }
}
