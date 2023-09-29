package cl.rs.bci.exercise.BCIExcercise.repository;

import cl.rs.bci.exercise.BCIExcercise.client.SignClient;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;
import cl.rs.bci.exercise.BCIExcercise.repository.impl.SignRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cl.rs.bci.exercise.BCIExcercise.fixture.SignFixture.getSaveSignDTO;
import static cl.rs.bci.exercise.BCIExcercise.fixture.SignFixture.makeSignResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignRepositoryTest {

    @InjectMocks
    private SignRepositoryImpl repository;

    @Mock
    private SignClient signClient;

    @BeforeEach
    public void setUp(){
        this.repository = new SignRepositoryImpl(signClient);
    }

    private static final String MESSAGE_RESPONSE_OK = "OK";

    @Test
    void saveSignTest(){
        when(signClient.saveSign(any())).thenReturn(makeSignResponse());
        SignResponse response = repository.saveSign(any());
        Assertions.assertThat(response.getId()).isNotEmpty();
        Assertions.assertThat(response.getId()).isEqualTo("a93ee646-7430-462d-ba48-6cef4874dc25");
        Assertions.assertThat(response.getLastLogin()).isNull();
        Assertions.assertThat(response.getCreated()).isNotNull();
        Assertions.assertThat(response.isActive()).isTrue();
    }

    @Test
    void loginTest(){
        when(signClient.findEmail(any())).thenReturn(getSaveSignDTO());
        SaveSign response = repository.findEmail(any());
        Assertions.assertThat(response.getId()).isNotEmpty();
        Assertions.assertThat(response.getId()).isEqualTo("a93ee646-7430-462d-ba48-6cef4874dc25");
        Assertions.assertThat(response.getLastLogin()).isNull();
        Assertions.assertThat(response.getCreated()).isNotNull();
    }

    @Test
    void validateAccountTest(){
        when(signClient.validateAccount(any())).thenReturn(getSaveSignDTO());
        SaveSign response = repository.validateAccount(any());
        Assertions.assertThat(response.getId()).isNotEmpty();
        Assertions.assertThat(response.getId()).isEqualTo("a93ee646-7430-462d-ba48-6cef4874dc25");
        Assertions.assertThat(response.getLastLogin()).isNull();
        Assertions.assertThat(response.getCreated()).isNotNull();
    }
}
