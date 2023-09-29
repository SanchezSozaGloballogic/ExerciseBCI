package cl.rs.bci.exercise.BCIExcercise.fixture

import cl.rs.bci.exercise.BCIExcercise.domain.*
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant

class SignFixtureAux extends Specification {

    static SignRequest getSignRequest(){
        SignRequest request = new SignRequest()
        request.name = 'Juan Perez'
        request.email = 'password@gmail.com'
        request.password = 'a2asfGfdfdf4'
        List<Phone> phones = new ArrayList<Phone>()
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode('+56')
                .build())
        request.phones = phones
        return request
    }

    static SaveSign getSaveSignDTO(){
        List<Phone> phones = new ArrayList<Phone>()
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode('+56')
                .build())
        return SaveSign.builder()
                .id(UUID.randomUUID().toString())
                .created(new Date())
                .isActive(true)
                .name('Juan Perez')
                .email('password@gmail.com')
                .password('a2asfGfdfdf4')
                .phones(phones)
                .build()
    }

    static SignResponse makeSignResponse(){
        return SignResponse.builder()
                .id('a93ee646-7430-462d-ba48-6cef4874dc25')
                .created(new Date())
                .lastLogin(null)
                .token('eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSG9sYSBNdW5kbyIsImVtYWlsIjoiYWxvaGFAYWxvaGEuY2wiLCJzdWIiOiJIb2xhIE11bmRvIiwianRpIjoiY2E5ZWFhZmUtNzRjZC00MzE2LWE1YzUtYjQ4YmQ2NzUxMzE2IiwiaWF0IjoxNjkyNTc4NzUwLCJleHAiOjE2OTI1NzkwNTB9.QsTUkR65iKC91ahHvYEh5pvlj0FvwCXrB7so46Nu96E')
                .isActive(true)
                .build()
    }

    static SaveSign getSaveSignDTOBadEmail(){
        List<Phone> phones = new ArrayList<Phone>()
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode('+56')
                .build())
        return SaveSign.builder()
                .id(UUID.randomUUID().toString())
                .created(new Date())
                .isActive(true)
                .name('Juan Perez')
                .email('test')
                .password('a2asfGfdfdf4')
                .phones(phones)
                .build()
    }

    static Error makeErrorBadEmail(){
        return Error.builder()
                .codigo(1)
                .detail('Correo es invalido, favor ingresar un correo valido')
                .timestamp(Timestamp.from(Instant.now()))
                .build()
    }

    static SignRequest getSignRequestBadEmail(){
        SignRequest request = new SignRequest()
        request.name = 'Juan Perez'
        request.email = 'password'
        request.password = 'a2asfGfdfdf4'
        List<Phone> phones = new ArrayList<Phone>()
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode('+56')
                .build())
        request.phones = phones
        return request
    }

    static SignRequest getSignRequestBadPassword(){
        SignRequest request = new SignRequest()
        request.name = 'Juan Perez'
        request.email = 'password@gmail.com'
        request.password = 'dsvbdgfadsbga'
        List<Phone> phones = new ArrayList<Phone>()
        phones.add(Phone.builder()
                .number(123)
                .cityCode(2)
                .countryCode('+56')
                .build())
        request.phones = phones
        return request
    }

    static String getEmailToken(){
        return 'aloha@aloha.com'
    }
}
