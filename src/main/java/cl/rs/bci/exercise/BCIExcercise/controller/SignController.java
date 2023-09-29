package cl.rs.bci.exercise.BCIExcercise.controller;

import cl.rs.bci.exercise.BCIExcercise.domain.RequestLogin;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignRequest;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;
import cl.rs.bci.exercise.BCIExcercise.service.SignService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignController {

    private SignService service;

    public SignController(SignService service){
        this.service = service;
    }


    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignResponse> signUp (@RequestBody SignRequest request){
        return ResponseEntity.ok(service.sign(request));
    }

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveSign> login (@RequestParam String token){
        return ResponseEntity.ok(service.login(token));
    }
}
