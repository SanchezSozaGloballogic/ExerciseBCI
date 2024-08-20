package cl.rs.bci.exercise.BCIExcercise.service.impl;

import cl.rs.bci.exercise.BCIExcercise.domain.*;
import cl.rs.bci.exercise.BCIExcercise.domain.Error;
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository;
import cl.rs.bci.exercise.BCIExcercise.service.SignService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SignServiceImpl implements SignService {

    private final SignRepository repository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public SignServiceImpl(SignRepository repository){
        this.repository = repository;
    }

    @Override
    public SignResponse sign(SignRequest request) {
        SignResponse response = new SignResponse();
        try{
            if(validateMail(request.getEmail())){
                if(validatePassword(request.getPassword())){
                    UserEntity user = repository.findByEmail(request.getEmail());
                    if(user != null){
                        return userExistsResponse();
                    }
                    return createAndSaveUserEntity(request);
                }
            }
        }catch (GeneralException ex){
            List<Error> errors = new ArrayList<>();
            Error error = new Error();
            error.setCode(ex.getCodeError());
            error.setDescription(ex.getMessage());
            error.setTimestamp(Timestamp.from(Instant.now()));
            errors.add(error);
            response.setError(errors);
        }
        return response;
    }

    @Override
    public SaveSign login(String token) {
        String email = validateToken(token);
        if(email != null){
            UserEntity user = repository.findByEmail(email);
            if(user != null){
                UserEntity updatedUser = updateUserAccount(user);
                List<Phone> phones = mapPhonesToPhoneEntities(updatedUser.getPhones());
                return constructResponse(updatedUser, phones);
            }
        }
        return createTokenExpiredResponse();
    }

    private SignResponse userExistsResponse() {
        SignResponse response = new SignResponse();
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setCode(3);
        error.setTimestamp(Timestamp.from(Instant.now()));
        error.setDescription("Usuario ya existe, favor ingrese un nuevo usuario");
        errors.add(error);
        response.setError(errors);
        return response;
    }

    private SignResponse createAndSaveUserEntity(SignRequest request) {
        List<PhoneEntity> phoneEntities = Objects.isNull(request.getPhones())?null:convertToPhoneEntities(request.getPhones());
        String encodedPassword = encodePassword(request.getPassword());
        UserEntity user = new UserEntity(UUID.randomUUID().toString(), Date.from(Instant.now()), null, createToken(request.getName(), request.getEmail()), true, request.getName(), request.getEmail(),
                encodedPassword, phoneEntities);
        UserEntity savedEntity = repository.save(user);
        return convertToSignResponse(savedEntity);
    }

    private UserEntity updateUserAccount(UserEntity user){
        UserEntity userAux = new UserEntity(user.getIdUser(), user.getCreated(), new Date(System.currentTimeMillis()),
                user.getToken(), user.isActive(), user.getName(), user.getEmail(), user.getPassword(), user.getPhones());
        return repository.save(userAux);
    }

    private List<PhoneEntity> convertToPhoneEntities(List<Phone> phones) {
        return phones.stream()
                .map(phone -> modelMapper.map(phone, PhoneEntity.class))
                .collect(Collectors.toList());
    }

    private List<Phone> mapPhonesToPhoneEntities(List<PhoneEntity> phoneEntities){
        return phoneEntities.stream()
                .map(phone -> modelMapper.map(phone, Phone.class))
                .collect(Collectors.toList());
    }

    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private String decodePassword(String password) {
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        return new String(decodedBytes);
    }

    private SignResponse convertToSignResponse(UserEntity entity) {
        return SignResponse.builder()
                .id(entity.getIdUser())
                .created(entity.getCreated())
                .lastLogin(entity.getLastLogin())
                .token(entity.getToken())
                .isActive(entity.isActive())
                .build();
    }

    private SaveSign constructResponse(UserEntity entity, List<Phone> phones){
        return SaveSign.builder()
                .id(entity.getIdUser())
                .created(entity.getCreated())
                .lastLogin(entity.getLastLogin())
                .token(createToken(entity.getName(), entity.getEmail()))
                .isActive(entity.isActive())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(decodePassword(entity.getPassword()))
                .phones(phones)
                .build();
    }

    private SaveSign createTokenExpiredResponse() {
        SaveSign request = new SaveSign();
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setCode(4);
        error.setDescription("Token expirado");
        error.setTimestamp(Timestamp.from(Instant.now()));
        errors.add(error);
        request.setError(errors);
        return request;
    }

    public static boolean validateMail(String email) {
        if(email!=null){
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mather = pattern.matcher(email);
            if(mather.find()){
                return true;
            }
            throw new GeneralException("Correo es invalido, favor ingresar un correo valido",1);
        }
        throw new GeneralException("Correo no puede ser nulo",3);

    }

    public static boolean validatePassword(String email) {
        if(email!=null){
            Pattern pattern = Pattern.compile("^(?=(?:[^A-Z]*[A-Z]){1}[^A-Z]*$)(?=(?:[^0-9]*[0-9]){2}[^0-9]*$).{8,12}$");
            Matcher mather = pattern.matcher(email);
            if(mather.find()){
                return true;
            }
            throw new GeneralException("Contraseña no cumple estandar, favor agregar una mayusula y dos numeros, " +
                    "largo maximo de 12 caracteres y minimo de 8 caracteres", 2);
        }
        throw new GeneralException("Contraseña no puede ser nula",4);
    }

    private String createToken (String name, String email){
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode("QkNJRXhjZXJjaXNlLTE0LzA4LzIwMjMtU2FudGlhZ28vQ2hpbGU="),
                SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .claim("name", name)
                .claim("email", email)
                .setSubject(name)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
    }

    private String validateToken(String token){
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode("QkNJRXhjZXJjaXNlLTE0LzA4LzIwMjMtU2FudGlhZ28vQ2hpbGU="),
                SignatureAlgorithm.HS256.getJcaName());
        try{
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token);

            return jwt.getBody().get("email").toString();
        }catch (Exception ex){
            return null;
        }
    }
}
