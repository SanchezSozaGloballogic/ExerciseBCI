package cl.rs.bci.exercise.BCIExcercise.service

import cl.rs.bci.exercise.BCIExcercise.domain.GeneralException
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository
import cl.rs.bci.exercise.BCIExcercise.service.impl.SignServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

import static cl.rs.bci.exercise.BCIExcercise.fixture.SignFixture.*
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.when

@ExtendWith(MockitoExtension.class)
class SignServiceImplTest extends Specification {

    @InjectMocks
    private SignServiceImpl service

    @Mock
    private SignRepository repository


    def setup(){
        MockitoAnnotations.openMocks(this);
    }


    def "save sign test ok"(){
        given:
        when(repository.save(org.mockito.ArgumentMatchers.any())).thenReturn(userEntityDTO)
        SignResponse response = service.sign(signRequest)
        Assertions.assertThat(response.id).isNotEmpty()
        Assertions.assertThat(response.id).isEqualTo('a93ee646-7430-462d-ba48-6cef4874dc25')
        Assertions.assertThat(response.lastLogin).isNull()
        Assertions.assertThat(response.created).isNotNull()
        expect:
        Assertions.assertThat(response.active).isTrue()
    }


    def "save sign user exist test"(){
        given:
        when(repository.findByEmail(org.mockito.ArgumentMatchers.any())).thenReturn(userEntityDTO)
        SignResponse response = service.sign(signRequest)
        Assertions.assertThat(response.code).isEqualTo(3)
        Assertions.assertThat(response.description).isEqualTo('Usuario ya existe, favor ingrese un nuevo usuario')
        expect:
        Assertions.assertThat(response.timestamp).isNotNull()
    }


    def "save sign bad email"(){
        given:
        SignResponse response = new SignResponse()
        expect:
        try{
            service.sign(signRequestBadEmail)
        }catch (GeneralException ex){
            Assertions.assertThat(response.code).isNotNull()
            Assertions.assertThat(response.code).isEqualTo(1)
            Assertions.assertThat(response.description).isNotNull()
            Assertions.assertThat(response.description).isEqualTo('Correo es invalido, favor ingresar un correo valido')
            Assertions.assertThat(response.timestamp).isNotNull()
        }
    }


    def "save sign bad password"(){
        given:
        SignResponse response = new SignResponse()
        expect:
        try{
            service.sign(signRequestBadPassword)
        }catch (GeneralException ex) {
            Assertions.assertThat(response.code).isNotNull()
            Assertions.assertThat(response.code).isEqualTo(2)
            Assertions.assertThat(response.description).isNotNull()
            Assertions.assertThat(response.description).isEqualTo('Contraseña no cumple estandar, favor agregar una mayusula y dos numeros, ' +
                    'largo maximo de 12 caracteres y minimo de 8 caracteres')
        }
    }


    def "login test o k"(){
        given:
        when(repository.findByEmail(anyString())).thenReturn(userEntityDTO)
        when(repository.save(any())).thenReturn(userEntityDTO)
        SaveSign response = service.login(ReflectionTestUtils.invokeMethod(service, 'createToken', 'Juan Perez', 'password@gmail.com'))
        Assertions.assertThat(response.id).isNotEmpty()
        Assertions.assertThat(response.id).isEqualTo('a93ee646-7430-462d-ba48-6cef4874dc25')
        Assertions.assertThat(response.lastLogin).isNull()
        expect:
        Assertions.assertThat(response.created).isNotNull()
    }


    def "login test null"(){
        given:
        SaveSign response = service.login(null)
        expect:
        Assertions.assertThat(response.id).isNull()
    }
}
