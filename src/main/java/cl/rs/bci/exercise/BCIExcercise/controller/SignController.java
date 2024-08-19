package cl.rs.bci.exercise.BCIExcercise.controller;

import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignRequest;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;
import cl.rs.bci.exercise.BCIExcercise.service.SignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignController {

    private final SignService service;

    public SignController(SignService service){
        this.service = service;
    }


    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignResponse> signUp (@RequestBody SignRequest request) {
        SignResponse response = service.sign(request);
        boolean hasError = response.getCode() != null;
        return createResponseEntity(response, hasError ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveSign> login (@RequestParam String token){
        SaveSign response = service.login(token);
        boolean hasError = response.getCode() != null;
        return createResponseEntity(response, hasError ? HttpStatus.UNAUTHORIZED : HttpStatus.OK);
    }

    private <T> ResponseEntity<T> createResponseEntity(T response, HttpStatus status) {
        return new ResponseEntity<>(response, status);
    }
}
