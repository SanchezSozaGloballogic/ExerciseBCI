package cl.rs.bci.exercise.BCIExcercise.controller

import cl.rs.bci.exercise.BCIExcercise.domain.SignRequest
import cl.rs.bci.exercise.BCIExcercise.service.SignService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.when

@ExtendWith(MockitoExtension.class)
class SignControllerTest extends Specification {

    @InjectMocks private SignController controller

    @Mock private SignService service


    def "save sing ok"(){
        given:
        when(service.sign(any(SignRequest.class))).thenReturn(new Object())
        ResponseEntity<Object> responseEntity = controller.signUp(new SignRequest())
        Assertions.assertThat(responseEntity).isNotNull()
        Assertions.assertThat(responseEntity.statusCode).isEqualByComparingTo(HttpStatus.OK)
        Object object = responseEntity.body
        expect:
        Assertions.assertThat(object).isNotNull()
    }


    def "find email"(){
        given:
        when(service.login(any(String.class))).thenReturn(new Object())
        ResponseEntity<Object> responseEntity = controller.login(anyString())
        Assertions.assertThat(responseEntity).isNotNull()
        Assertions.assertThat(responseEntity.statusCode).isEqualByComparingTo(HttpStatus.OK)
        Object object = responseEntity.body
        expect:
        Assertions.assertThat(object).isNotNull()
    }
}
