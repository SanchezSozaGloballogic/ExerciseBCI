package cl.rs.bci.exercise.BCIExcercise.service

import cl.rs.bci.exercise.BCIExcercise.fixture.SignFixture
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository
import cl.rs.bci.exercise.BCIExcercise.service.impl.SignServiceImpl
import org.assertj.core.api.Assertions
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class SignServiceImplTest extends Specification {

    private SignServiceImpl service

    private SignRepository repository = Mock()

    def request
    def responseDto
    def badRequestEmail
    def badRequestPassword
    def emailNull
    def passwordNull


    def setup(){
        service = new SignServiceImpl(repository)
        request = SignFixture.signRequest
        responseDto = SignFixture.userEntityDTO
        badRequestEmail = SignFixture.signRequestBadEmail
        badRequestPassword = SignFixture.signRequestBadPassword
        emailNull = SignFixture.signRequestEmailNull
        passwordNull = SignFixture.signRequestPasswordNull
    }


    def "save sign test ok"(){
        when:
        def response = service.sign(request)

        then:
        1 * repository.findByEmail(_) >> null
        1 * repository.save(_) >> responseDto
        Assertions.assertThat(response.id).isNotEmpty()
        Assertions.assertThat(response.id).isEqualTo('a93ee646-7430-462d-ba48-6cef4874dc25')
        Assertions.assertThat(response.lastLogin).isNull()
        Assertions.assertThat(response.created).isNotNull()
        Assertions.assertThat(response.isActive).isTrue()
    }


    def "save sign user exist test"(){
        when:
        def response = service.sign(request)

        then:
        1 * repository.findByEmail(_) >> responseDto

        Assertions.assertThat(response.getError().stream().findAny().get().code).isEqualTo(3)
        Assertions.assertThat(response.getError().stream().findAny().get().description).isEqualTo('Usuario ya existe, favor ingrese un nuevo usuario')
        Assertions.assertThat(response.getError().stream().findAny().get().timestamp).isNotNull()
    }


    def "save sign bad email"(){
        when:
        def response = service.sign(badRequestEmail)

        then:
        Assertions.assertThat(response.getError().stream().findAny().get().code).isNotNull()
        Assertions.assertThat(response.getError().stream().findAny().get().code).isEqualTo(1)
        Assertions.assertThat(response.getError().stream().findAny().get().description).isNotNull()
        Assertions.assertThat(response.getError().stream().findAny().get().description).isEqualTo('Correo es invalido, favor ingresar un correo valido')
        Assertions.assertThat(response.getError().stream().findAny().get().timestamp).isNotNull()
    }


    def "save sign bad password"(){
        when:
        def response = service.sign(badRequestPassword)

        then:
        Assertions.assertThat(response.getError().stream().findAny().get().code).isNotNull()
        Assertions.assertThat(response.getError().stream().findAny().get().code).isEqualTo(2)
        Assertions.assertThat(response.getError().stream().findAny().get().description).isNotNull()
        Assertions.assertThat(response.getError().stream().findAny().get().description).isEqualTo('Contraseña no cumple estandar, favor agregar una mayusula y dos numeros, ' +
                'largo maximo de 12 caracteres y minimo de 8 caracteres')
        Assertions.assertThat(response.getError().stream().findAny().get().timestamp).isNotNull()
    }

    def "save sign email null"(){
        when:
        def response = service.sign(emailNull)

        then:
        Assertions.assertThat(response.getError().stream().findAny().get().code).isNotNull()
        Assertions.assertThat(response.getError().stream().findAny().get().code).isEqualTo(3)
        Assertions.assertThat(response.getError().stream().findAny().get().description).isNotNull()
        Assertions.assertThat(response.getError().stream().findAny().get().description).isEqualTo('Correo no puede ser nulo')
        Assertions.assertThat(response.getError().stream().findAny().get().timestamp).isNotNull()
    }

    def "save sign password null"(){
        when:
        def response = service.sign(passwordNull)

        then:
        Assertions.assertThat(response.getError().stream().findAny().get().code).isNotNull()
        Assertions.assertThat(response.getError().stream().findAny().get().code).isEqualTo(4)
        Assertions.assertThat(response.getError().stream().findAny().get().description).isNotNull()
        Assertions.assertThat(response.getError().stream().findAny().get().description).isEqualTo('Contraseña no puede ser nula')
        Assertions.assertThat(response.getError().stream().findAny().get().timestamp).isNotNull()
    }



    def "login test ok"(){
        when:
        def response = service.login(ReflectionTestUtils.invokeMethod(service, 'createToken', 'Juan Perez', 'password@gmail.com'))

        then:
        1 * repository.findByEmail(_) >> responseDto
        1 * repository.save(_) >> responseDto
        Assertions.assertThat(response.id).isNotEmpty()
        Assertions.assertThat(response.id).isEqualTo('a93ee646-7430-462d-ba48-6cef4874dc25')
        Assertions.assertThat(response.lastLogin).isNull()
        Assertions.assertThat(response.created).isNotNull()
    }


    def "login test null"(){
        when:
        def response = service.login(null)
        then:
        Assertions.assertThat(response.id).isNull()
    }
}
