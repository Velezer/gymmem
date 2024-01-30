package ariefsyaifu.gymmem.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ariefsyaifu.gymmem.user.dto.user.UpdateUserMeRequestBody;
import ariefsyaifu.gymmem.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMe(
            HttpServletRequest request) {
        Claims c = (Claims) request.getAttribute("claims");
        return ResponseEntity.ok(c);
    }

    @GetMapping("/me/creditCard")
    public ResponseEntity<Object> getMeCreditCard(
            HttpServletRequest request) {
        Claims c = (Claims) request.getAttribute("claims");
        return ResponseEntity.ok(userService.getMeCreditCard(c));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(@Valid @RequestBody UpdateUserMeRequestBody params, HttpServletRequest request) {
        Claims c = (Claims) request.getAttribute("claims");
        userService.updateMe(params, c);
        return ResponseEntity.noContent().build();
    }

}
