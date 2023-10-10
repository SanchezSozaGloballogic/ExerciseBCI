package cl.rs.bci.exercise.BCIExcercise.controller

import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign
import cl.rs.bci.exercise.BCIExcercise.domain.SignRequest
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse
import cl.rs.bci.exercise.BCIExcercise.service.SignService
import org.assertj.core.api.Assertions
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class SignControllerTest extends Specification {

    @InjectMocks private SignController controller;

    @Mock private SignService service;


    def setup(){
        MockitoAnnotations.openMocks(this);
    }


    def "test save sign ok"(){
        given:
        when(service.sign(any(SignRequest.class))).thenReturn(new SignResponse());
        when:
        ResponseEntity<SignResponse> responseEntity = controller.signUp(new SignRequest());
        then:
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.statusCode).isEqualByComparingTo(HttpStatus.OK);
        Object object = responseEntity.body;
        Assertions.assertThat(object).isNotNull();
    }


    def "test login ok"(){
        given:
        when(service.login(org.mockito.ArgumentMatchers.any())).thenReturn(new SaveSign());
        when:
        ResponseEntity<SaveSign> responseEntity = controller.login(any())
        then:
        Assertions.assertThat(responseEntity).isNotNull()
        Assertions.assertThat(responseEntity.statusCode).isEqualByComparingTo(HttpStatus.OK)
        Object object = responseEntity.body
        Assertions.assertThat(object).isNotNull()
    }
}
