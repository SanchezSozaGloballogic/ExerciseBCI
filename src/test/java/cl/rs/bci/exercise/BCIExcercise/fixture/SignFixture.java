package cl.rs.bci.exercise.BCIExcercise.fixture;

import cl.rs.bci.exercise.BCIExcercise.domain.Error;
import cl.rs.bci.exercise.BCIExcercise.domain.Phone;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignRequest;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SignFixture {

    public static SignRequest getSignRequest(){
        SignRequest request = new SignRequest();
        request.setName("Juan Perez");
        request.setEmail("password@gmail.com");
        request.setPassword("a2asfGfdfdf4");
        List<Phone> phones = new ArrayList<Phone>();
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode("+56")
                .build());
        request.setPhones(phones);
        return request;
    }

    public static SaveSign getSaveSignDTO(){
        List<Phone> phones = new ArrayList<Phone>();
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode("+56")
                .build());

        SaveSign saveSign = new SaveSign();
        saveSign.setPhones(phones);
        saveSign.setEmail("password@gmail.com");
        saveSign.setPassword("a2asfGfdfdf4");
        saveSign.setCreated(new Date());
        saveSign.setName("Juan Perez");
        saveSign.setActive(true);
        saveSign.setId("a93ee646-7430-462d-ba48-6cef4874dc25");

        return saveSign;
    }

    public static SignResponse makeSignResponse(){
        SignResponse response = new SignResponse();
        response.setId("a93ee646-7430-462d-ba48-6cef4874dc25");
        response.setCreated(new Date());
        response.setLastLogin(null);
        response.setToken("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSG9sYSBNdW5kbyIsImVtYWlsIjoiYWxvaGFAYWxvaGEuY2wiLCJzdWIiOiJIb2xhIE11bmRvIiwianRpIjoiY2E5ZWFhZmUtNzRjZC00MzE2LWE1YzUtYjQ4YmQ2NzUxMzE2IiwiaWF0IjoxNjkyNTc4NzUwLCJleHAiOjE2OTI1NzkwNTB9.QsTUkR65iKC91ahHvYEh5pvlj0FvwCXrB7so46Nu96E");
        response.setActive(true);
        return response;
    }

    public static SaveSign getSaveSignDTOBadEmail(){
        List<Phone> phones = new ArrayList<Phone>();
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode("+56")
                .build());
        SaveSign saveSign = new SaveSign();
        saveSign.setPhones(phones);
        saveSign.setEmail("test");
        saveSign.setPassword("a2asfGfdfdf4");
        saveSign.setCreated(new Date());
        saveSign.setName("Juan Perez");
        saveSign.setActive(true);
        saveSign.setId(UUID.randomUUID().toString());
        return saveSign;
    }

    public static Error makeErrorBadEmail(){
        return Error.builder()
                .codigo(1)
                .detail("Correo es invalido, favor ingresar un correo valido")
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    public static SignRequest getSignRequestBadEmail(){
        SignRequest request = new SignRequest();
        request.setName("Juan Perez");
        request.setEmail("password");
        request.setPassword("a2asfGfdfdf4");
        List<Phone> phones = new ArrayList<Phone>();
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode("+56")
                .build());
        request.setPhones(phones);
        return request;
    }

    public static SignRequest getSignRequestBadPassword(){
        SignRequest request = new SignRequest();
        request.setName("Juan Perez");
        request.setEmail("password@gmail.com");
        request.setPassword("dsvbdgfadsbga");
        List<Phone> phones = new ArrayList<Phone>();
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode("+56")
                .build());
        request.setPhones(phones);
        return request;
    }

    public static String getEmailToken(){
        return "aloha@aloha.com";
    }
}
