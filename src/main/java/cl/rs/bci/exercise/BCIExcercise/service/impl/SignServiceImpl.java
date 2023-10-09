package cl.rs.bci.exercise.BCIExcercise.service.impl;

import cl.rs.bci.exercise.BCIExcercise.domain.*;
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository;
import cl.rs.bci.exercise.BCIExcercise.service.SignService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SignServiceImpl implements SignService {

    @Value("${jwt.secret}")
    private String secret;

    private SignRepository repository;

    private static ModelMapper modelMapper = new ModelMapper();

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
                    UserEntity request1 = repository.findByEmail(login.getUser());
                    if(request1 != null){
                        response.setCode(3);
                        response.setTimestamp(Timestamp.from(Instant.now()));
                        response.setDescription("Usuario ya existe, favor ingrese un nuevo usuario");
                        return response;
                    }


                    List<PhoneEntity> phoneEntity = request.getPhones()
                            .stream()
                            .map(user -> modelMapper.map(user, PhoneEntity.class))
                            .collect(Collectors.toList());
                    UserEntity user = new UserEntity(UUID.randomUUID().toString(), Date.from(Instant.now()), null, createToken(request.getName(), request.getEmail()), true, request.getName(), request.getEmail(),
                            Base64.getEncoder().encodeToString(request.getPassword().getBytes()), phoneEntity);
                    UserEntity entity = repository.save(user);
                    return SignResponse.builder()
                            .id(entity.getIdUser())
                            .created(entity.getCreated())
                            .lastLogin(entity.getLastLogin())
                            .token(entity.getToken())
                            .isActive(entity.isActive())
                            .build();
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
        String email = validateToken(token);
        if(email != null){
            UserEntity user = repository.findByEmail(email);
            if(user != null){
                UserEntity userAux = new UserEntity(user.getIdUser(), user.getCreated(), new Date(System.currentTimeMillis()), user.getToken(), user.isActive(), user.getName(), user.getEmail(),
                        user.getPassword(), user.getPhones());
                UserEntity entity = repository.save(userAux);
                List<Phone> phones = user.getPhones()
                        .stream()
                        .map(phone -> modelMapper.map(phone, Phone.class))
                        .collect(Collectors.toList());
                return SaveSign.builder()
                        .id(entity.getIdUser())
                        .created(entity.getCreated())
                        .lastLogin(entity.getLastLogin())
                        .token(createToken(entity.getName(), entity.getEmail()))
                        .isActive(entity.isActive())
                        .name(entity.getName())
                        .email(entity.getEmail())
                        .password(entity.getPassword())
                        .phones(phones)
                        .build();
            }
        }
        SaveSign request = new SaveSign();
        request.setCode(4);
        request.setDescription("Token expirado");
        request.setTimestamp(Timestamp.from(Instant.now()));
        return request;
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

    private String createToken (String name, String email){
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        String jwtToken = Jwts.builder()
                .claim("name", name)
                .claim("email", email)
                .setSubject(name)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
        return jwtToken;
    }

    private String validateToken(String token){
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
        try{
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token);
            String mail = jwt.getBody().get("email").toString();

            return mail;
        }catch (Exception ex){
            return null;
        }
    }
}
