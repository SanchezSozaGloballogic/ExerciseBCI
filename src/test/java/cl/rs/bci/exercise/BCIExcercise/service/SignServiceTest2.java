package cl.rs.bci.exercise.BCIExcercise.service;

import cl.rs.bci.exercise.BCIExcercise.domain.*;
import cl.rs.bci.exercise.BCIExcercise.domain.Error;
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository;
import cl.rs.bci.exercise.BCIExcercise.service.impl.SignServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static cl.rs.bci.exercise.BCIExcercise.fixture.SignFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@ExtendWith(MockitoExtension.class)
public class SignServiceTest2 {

    @InjectMocks
    private SignServiceImpl service;

    @Mock
    private SignRepository repository;

    @BeforeEach
    public void setUp(){
        this.service = new SignServiceImpl(repository);
    }

    @Test
    void saveSignTestOK(){
        when(repository.save(any())).thenReturn(getUserEntityDTO());
        SignResponse response = service.sign(getSignRequest());
        Assertions.assertThat(response.getId()).isNotEmpty();
        Assertions.assertThat(response.getId()).isEqualTo("a93ee646-7430-462d-ba48-6cef4874dc25");
        Assertions.assertThat(response.getLastLogin()).isNull();
        Assertions.assertThat(response.getCreated()).isNotNull();
        Assertions.assertThat(response.getIsActive()).isTrue();
    }

    @Test
    void saveSignUserExistTest(){
        when(repository.findByEmail(any())).thenReturn(getUserEntityDTO());
        SignResponse response = service.sign(getSignRequest());
        Error error = response.getError().stream().findFirst().orElse(new Error());
        Assertions.assertThat(error.getCode()).isEqualTo(3);
        Assertions.assertThat(error.getDescription()).isEqualTo("Usuario ya existe, favor ingrese un nuevo usuario");
        Assertions.assertThat(error.getTimestamp()).isNotNull();
    }

    @Test
    void saveSignBadEmail(){
        SignResponse response = new SignResponse();
        try{
            service.sign(getSignRequestBadEmail());
        }catch (GeneralException ex){
            Error error = response.getError().stream().findFirst().orElse(new Error());
            Assertions.assertThat(error.getCode()).isNotNull();
            Assertions.assertThat(error.getCode()).isEqualTo(1);
            Assertions.assertThat(error.getDescription()).isNotNull();
            Assertions.assertThat(error.getDescription()).isEqualTo("Correo es invalido, favor ingresar un correo valido");
            Assertions.assertThat(error.getTimestamp()).isNotNull();
        }
    }

    @Test
    void saveSignBadPassword(){
        SignResponse response = new SignResponse();
        try{
            service.sign(getSignRequestBadPassword());
        }catch (GeneralException ex) {
            Error error = response.getError().stream().findFirst().orElse(new Error());
            Assertions.assertThat(error.getCode()).isNotNull();
            Assertions.assertThat(error.getCode()).isEqualTo(2);
            Assertions.assertThat(error.getDescription()).isNotNull();
            Assertions.assertThat(error.getDescription()).isEqualTo("Contraseña no cumple estandar, favor agregar una mayusula y dos numeros, " +
                    "largo maximo de 12 caracteres y minimo de 8 caracteres");
        }
    }

    @Test
    void saveSignEmailNull(){
        SignResponse response = new SignResponse();
        try{
            service.sign(getSignRequestEmailNull());
        }catch (GeneralException ex) {
            Error error = response.getError().stream().findFirst().orElse(new Error());
            Assertions.assertThat(error.getCode()).isNotNull();
            Assertions.assertThat(error.getCode()).isEqualTo(3);
            Assertions.assertThat(error.getDescription()).isNotNull();
            Assertions.assertThat(error.getDescription()).isEqualTo("Correo no puede ser nulo");
        }
    }

    @Test
    void saveSignPasswordNull(){
        SignResponse response = new SignResponse();
        try{
            service.sign(getSignRequestPasswordNull());
        }catch (GeneralException ex) {
            Error error = response.getError().stream().findFirst().orElse(new Error());
            Assertions.assertThat(error.getCode()).isNotNull();
            Assertions.assertThat(error.getCode()).isEqualTo(4);
            Assertions.assertThat(error.getDescription()).isNotNull();
            Assertions.assertThat(error.getDescription()).isEqualTo("Contraseña no puede ser nula");
        }
    }

    @Test
    void loginTestOK(){
        when(repository.findByEmail(anyString())).thenReturn(getUserEntityDTO());
        when(repository.save(any())).thenReturn(getUserEntityDTO());
        SaveSign response = service.login(ReflectionTestUtils.invokeMethod(service, "createToken", "Juan Perez", "password@gmail.com"));
        Assertions.assertThat(response.getId()).isNotEmpty();
        Assertions.assertThat(response.getId()).isEqualTo("a93ee646-7430-462d-ba48-6cef4874dc25");
        Assertions.assertThat(response.getLastLogin()).isNull();
        Assertions.assertThat(response.getCreated()).isNotNull();
    }

    @Test
    void loginTestNull(){
        SaveSign response = service.login(null);
        Assertions.assertThat(response.getId()).isNull();
    }
}
