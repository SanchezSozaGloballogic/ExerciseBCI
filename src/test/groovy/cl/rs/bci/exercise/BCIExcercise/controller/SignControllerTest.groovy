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

    private SignController controller
    private SignService service = Mock()
    def request
    def response
    def responseSign


    def setup(){
        controller = new SignController(service)
        request = new SignRequest()
        response = new SignResponse()
        responseSign = new SaveSign()
    }


    def "test save sign ok"(){
        when:
        ResponseEntity<SignResponse> responseEntity = controller.signUp(request)

        then:
        1 * service.sign(_) >> response
        Assertions.assertThat(responseEntity).isNotNull()
        Assertions.assertThat(responseEntity.statusCode).isEqualByComparingTo(HttpStatus.OK)
        Object object = responseEntity.body
        Assertions.assertThat(object).isNotNull()
    }


    def "test login ok"(){
        when:
        ResponseEntity<SaveSign> responseEntity = controller.login("testToken")

        then:
        1 * service.login(_) >> responseSign
        Assertions.assertThat(responseEntity).isNotNull()
        Assertions.assertThat(responseEntity.statusCode).isEqualByComparingTo(HttpStatus.OK)
        Object object = responseEntity.body
        Assertions.assertThat(object).isNotNull()
    }
}
