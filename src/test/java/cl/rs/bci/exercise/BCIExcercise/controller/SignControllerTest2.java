package cl.rs.bci.exercise.BCIExcercise.controller;

import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignRequest;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;
import cl.rs.bci.exercise.BCIExcercise.service.SignService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignControllerTest2 {

    @InjectMocks private SignController controller;

    @Mock private SignService service;

    @Test
    void testSaveSignOK(){
        when(service.sign(any(SignRequest.class))).thenReturn(new SignResponse());
        ResponseEntity<SignResponse> responseEntity = controller.signUp(new SignRequest());
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Object object = responseEntity.getBody();
        Assertions.assertThat(object).isNotNull();
    }

    @Test
    void testLoginOK(){
        when(service.login(any())).thenReturn(new SaveSign());
        ResponseEntity<SaveSign> responseEntity = controller.login(any());
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Object object = responseEntity.getBody();
        Assertions.assertThat(object).isNotNull();
    }
}
