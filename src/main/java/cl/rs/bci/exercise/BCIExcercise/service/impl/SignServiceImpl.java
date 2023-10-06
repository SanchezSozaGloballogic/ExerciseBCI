package cl.rs.bci.exercise.BCIExcercise.service.impl;

import cl.rs.bci.exercise.BCIExcercise.domain.*;
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository;
import cl.rs.bci.exercise.BCIExcercise.service.SignService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SignServiceImpl implements SignService {

    @Value("${jwt.secret}")
    private String secret;

    private SignRepository repository;

    public SignServiceImpl(SignRepository repository){
        this.repository = repository;
    }


    @Override
    public SignResponse sign(SignRequest request) {
        SignResponse response = new SignResponse();
        try{
            if(validateMail(request.getEmail())){
                if(validatePassword(request.getPassword())){
                    RequestLogin login = RequestLogin.builder()
                            .user(request.getEmail())
                            .password(request.getPassword())
                            .build();
                    SaveSign request1 = repository.validateAccount(login);
                    if(request1 != null){
                        response.setCode(3);
                        response.setTimestamp(Timestamp.from(Instant.now()));
                        response.setDescription("Usuario ya existe, favor ingrese un nuevo usuario");
                        return response;
                    }

                    SaveSign saveSign = new SaveSign();
                    saveSign.setId(UUID.randomUUID().toString());
                    saveSign.setCreated(Date.from(Instant.now()));
                    saveSign.setActive(true);
                    saveSign.setEmail(request.getEmail());
                    saveSign.setName(request.getName());
                    saveSign.setPassword(request.getPassword());
                    saveSign.setPhones(request.getPhones());

                    response = repository.saveSign(saveSign);
                    return response;
                }
            }
        }catch (GeneralException ex){
            response.setCode(ex.getCodeError());
            response.setDescription(ex.getMessage());
            response.setTimestamp(Timestamp.from(Instant.now()));
        }
        return response;
    }

    @Override
    public SaveSign login(String token) {
        if(token != null && !token.isEmpty()){
            SaveSign request = repository.findEmail(token);
            if(request != null){
                return request;
            }
            request = new SaveSign();
            request.setCode(4);
            request.setDescription("Token expirado");
            request.setTimestamp(Timestamp.from(Instant.now()));
            return request;
        }
        return null;
    }

    public static boolean validateMail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if(mather.find()){
            return true;
        }
        throw new GeneralException("Correo es invalido, favor ingresar un correo valido",1);
    }

    public static boolean validatePassword(String email) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,12}$"
                );

        Matcher mather = pattern.matcher(email);
        if(mather.find()){
            return true;
        }
        throw new GeneralException("Contraseña no cumple estandar, favor agregar una mayusula y dos numeros, " +
                "largo maximo de 12 caracteres y minimo de 8 caracteres", 2);
    }
}
