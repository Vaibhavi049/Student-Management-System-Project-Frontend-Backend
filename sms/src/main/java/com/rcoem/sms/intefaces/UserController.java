package com.rcoem.sms.intefaces;

// import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.dto.UserDetails;
// import com.rcoem.sms.application.services.StudentService;
import com.rcoem.sms.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rcoem.sms.application.services.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDetails userDetails) {
        try {
            UserDetails insertedUserDetails = userService.registerUser(userDetails);
            return ResponseEntity.created(URI.create("/users/"+insertedUserDetails.getId())).body(insertedUserDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Registration failed: " + e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/sign-in")
    public ResponseEntity<?> signInUser(@RequestBody UserDetails userDetails) {
        try {
            UserDetails signedInUser = userService.signInUser(userDetails.getEmail(), userDetails.getPassword());
            if(signedInUser == null){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(signedInUser);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(new ErrorResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/check-email/{email}")
    public ResponseEntity<ValidationResponse> checkEmail(@PathVariable String email) {
        boolean exists = userServiceImpl.emailExists(email);
        return ResponseEntity.ok(new ValidationResponse(exists));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/check-mobile/{mobileNumber}")
    public ResponseEntity<ValidationResponse> checkMobile(@PathVariable String mobileNumber) {
        boolean exists = userServiceImpl.mobileExists(mobileNumber);
        return ResponseEntity.ok(new ValidationResponse(exists));
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserDetails userDetails) {
        try {
            userDetails.setId(id);
            UserDetails updatedUser = userServiceImpl.updateUser(userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            UserDetails userDetails = new UserDetails();
            userDetails.setId(id);
            UserDetails user = userService.getUserDetails(userDetails);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Failed to get user: " + e.getMessage()));
        }
    }

    // Inner classes for response
    static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    static class ValidationResponse {
        private boolean exists;
        public ValidationResponse(boolean exists) {
            this.exists = exists;
        }
        public boolean isExists() {
            return exists;
        }
    }
}
