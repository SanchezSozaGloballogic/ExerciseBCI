package cl.rs.bci.exercise.BCIExcercise.service

import cl.rs.bci.exercise.BCIExcercise.domain.Error
import cl.rs.bci.exercise.BCIExcercise.domain.Phone
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository
import cl.rs.bci.exercise.BCIExcercise.service.impl.SignServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import spock.lang.Specification

import java.time.Instant

import static cl.rs.bci.exercise.BCIExcercise.fixture.SignFixture.*
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.when

class SignServiceImplTest extends Specification {


    private SignService service;

    private SignRepository repository;


    def setup(){
        repository = Mock(SignRepository);
        service = new SignServiceImpl(repository);
    }


    def "save sign test ok"(){
        given:
            SaveSign saveSign = new SaveSign();
            saveSign.setId(UUID.randomUUID().toString());
            saveSign.setCreated(Date.from(Instant.now()));
            saveSign.setActive(true);
            saveSign.setEmail("password@gmail.com");
            saveSign.setName("Juan Perez");
            saveSign.setPassword("a2asfGfdfdf4");
            List<Phone> phones = new ArrayList<Phone>();
            phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode("+56")
                .build());
            saveSign.setPhones(phones);
            repository.saveSign(saveSign) >> SignResponse.builder()
                    .id("a93ee646-7430-462d-ba48-6cef4874dc25")
                    .created(new Date())
                    .lastLogin(null)
                    .token("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSG9sYSBNdW5kbyIsImVtYWlsIjoiYWxvaGFAYWxvaGEuY2wiLCJzdWIiOiJIb2xhIE11bmRvIiwianRpIjoiY2E5ZWFhZmUtNzRjZC00MzE2LWE1YzUtYjQ4YmQ2NzUxMzE2IiwiaWF0IjoxNjkyNTc4NzUwLCJleHAiOjE2OTI1NzkwNTB9.QsTUkR65iKC91ahHvYEh5pvlj0FvwCXrB7so46Nu96E")
                    .isActive(true)
                    .build()
        when:
            def response = service.sign(signRequest)
        then:
            Assertions.assertThat(response.id).isNotEmpty();
            Assertions.assertThat(response.id).isEqualTo('a93ee646-7430-462d-ba48-6cef4874dc25');
            Assertions.assertThat(response.lastLogin).isNull();
            Assertions.assertThat(response.created).isNotNull();
            Assertions.assertThat(response.active).isTrue();
    }


    def "save sign test error email"(){
        given:
        Error response = (Error) service.sign(signRequestBadEmail)
        Assertions.assertThat(response.codigo).isNotNull()
        Assertions.assertThat(response.codigo).isEqualTo(1)
        Assertions.assertThat(response.detail).isNotNull()
        Assertions.assertThat(response.detail).isEqualTo('Correo es invalido, favor ingresar un correo valido')
        expect:
        Assertions.assertThat(response.timestamp).isNotNull()
    }


    def "save sign test error passsword"(){
        given:
        Error response = (Error) service.sign(signRequestBadPassword)
        Assertions.assertThat(response.codigo).isNotNull()
        Assertions.assertThat(response.codigo).isEqualTo(2)
        Assertions.assertThat(response.detail).isNotNull()
        Assertions.assertThat(response.detail).isEqualTo('Contraseña no cumple estandar, favor agregar una mayusula y dos numeros, ' +
                'largo maximo de 12 caracteres y minimo de 8 caracteres')
        expect:
        Assertions.assertThat(response.timestamp).isNotNull()
    }


    def "login test ok"(){
        given:
        when(repository.findEmail(anyString())).thenReturn(saveSignDTO)
        when(service.validateToken(anyString())).thenReturn(emailToken)
        SignResponse response = (SignResponse) service.login(anyString())
        Assertions.assertThat(response.id).isNotEmpty()
        Assertions.assertThat(response.id).isEqualTo('a93ee646-7430-462d-ba48-6cef4874dc25')
        Assertions.assertThat(response.lastLogin).isNull()
        Assertions.assertThat(response.created).isNotNull()
        expect:
        Assertions.assertThat(response.active).isTrue()
    }
}
