package cl.rs.bci.exercise.BCIExcercise.repository.impl;

import cl.rs.bci.exercise.BCIExcercise.client.SignClient;
import cl.rs.bci.exercise.BCIExcercise.domain.RequestLogin;
import cl.rs.bci.exercise.BCIExcercise.domain.SaveSign;
import cl.rs.bci.exercise.BCIExcercise.domain.SignResponse;
import cl.rs.bci.exercise.BCIExcercise.repository.SignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SignRepositoryImpl implements SignRepository {


    private SignClient client;

    @Autowired
    public SignRepositoryImpl(SignClient client){
        this.client = client;
    }

    @Override
    public SignResponse saveSign(SaveSign request) {
        return invoke(request, client::saveSign);
    }

    @Override
    public SaveSign findEmail(String token) {
        return invoke(token, client::findEmail);
    }

    @Override
    public SaveSign validateAccount(RequestLogin login) {
        return invoke(login, client::validateAccount);
    }

    private static <T,R> R invoke(T t, Predicated<T, R>p){
        try{
            return p.invoke(t);
        }catch (Exception ex){
            return null;
        }
    }
}
