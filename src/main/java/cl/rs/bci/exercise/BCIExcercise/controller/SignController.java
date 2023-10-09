package cl.rs.bci.exercise.BCIExcercise.controller;

import cl.rs.bci.exercise.BCIExcercise.domain.RequestLogin;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignRequest;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;
import cl.rs.bci.exercise.BCIExcercise.service.SignService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SignResponse> signUp (@RequestBody SignRequest request) {
        SignResponse response = service.sign(request);
            if (response.getCode() != null) {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveSign> login (@RequestParam String token){
        SaveSign response = service.login(token);
        if(response.getCode() != null){
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
