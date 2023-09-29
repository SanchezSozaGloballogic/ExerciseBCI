package cl.rs.bci.exercise.BCIExcercise.service;

import cl.rs.bci.exercise.BCIExcercise.domain.*;
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository;
import cl.rs.bci.exercise.BCIExcercise.service.impl.SignServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cl.rs.bci.exercise.BCIExcercise.fixture.SignFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
        when(repository.saveSign(any())).thenReturn(makeSignResponse());
        SignResponse response = service.sign(getSignRequest());
        Assertions.assertThat(response.getId()).isNotEmpty();
        Assertions.assertThat(response.getId()).isEqualTo("a93ee646-7430-462d-ba48-6cef4874dc25");
        Assertions.assertThat(response.getLastLogin()).isNull();
        Assertions.assertThat(response.getCreated()).isNotNull();
        Assertions.assertThat(response.isActive()).isTrue();
    }

    @Test
    void saveSignUserExistTest(){
        when(repository.validateAccount(any())).thenReturn(getSaveSignDTO());
        SignResponse response = service.sign(getSignRequest());
        Assertions.assertThat(response.getCode()).isEqualTo(3);
        Assertions.assertThat(response.getDescription()).isEqualTo("Usuario ya existe, favor ingrese un nuevo usuario");
        Assertions.assertThat(response.getTimestamp()).isNotNull();
    }

    @Test
    void saveSignBadEmail(){
        SignResponse response = new SignResponse();
        try{
            service.sign(getSignRequestBadEmail());
        }catch (GeneralException ex){
            Assertions.assertThat(response.getCode()).isNotNull();
            Assertions.assertThat(response.getCode()).isEqualTo(1);
            Assertions.assertThat(response.getDescription()).isNotNull();
            Assertions.assertThat(response.getDescription()).isEqualTo("Correo es invalido, favor ingresar un correo valido");
            Assertions.assertThat(response.getTimestamp()).isNotNull();
        }
    }

    @Test
    void saveSignBadPassword(){
        SignResponse response = new SignResponse();
        try{
            service.sign(getSignRequestBadPassword());
        }catch (GeneralException ex) {
            Assertions.assertThat(response.getCode()).isNotNull();
            Assertions.assertThat(response.getCode()).isEqualTo(2);
            Assertions.assertThat(response.getDescription()).isNotNull();
            Assertions.assertThat(response.getDescription()).isEqualTo("Contraseña no cumple estandar, favor agregar una mayusula y dos numeros, " +
                    "largo maximo de 12 caracteres y minimo de 8 caracteres");
        }
    }

    @Test
    void loginTestOK(){
        when(repository.findEmail(anyString())).thenReturn(getSaveSignDTO());
        SaveSign response = (SaveSign) service.login("test");
        Assertions.assertThat(response.getId()).isNotEmpty();
        Assertions.assertThat(response.getId()).isEqualTo("a93ee646-7430-462d-ba48-6cef4874dc25");
        Assertions.assertThat(response.getLastLogin()).isNull();
        Assertions.assertThat(response.getCreated()).isNotNull();
    }

    @Test
    void loginTestNull(){
        SaveSign response = (SaveSign) service.login(null);
        Assertions.assertThat(response).isNull();
    }
}
