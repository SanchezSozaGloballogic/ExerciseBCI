package cl.rs.bci.exercise.BCIExcercise.client;

import cl.rs.bci.exercise.BCIExcercise.domain.RequestLogin;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "consumeBD",
            url = "http://localhost:8083")
public interface SignClient {

    @PostMapping("/sign-up")
    SignResponse saveSign(@RequestBody SaveSign request);

    @GetMapping("findEmail")
    SaveSign findEmail(@RequestParam String token);

    @PostMapping("validateAccount")
    SaveSign validateAccount(@RequestBody RequestLogin login);

}
