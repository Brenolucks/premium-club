package brenolucks.premiumclub.service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean emailIsValid(String email) {
        return false;
    }
}
