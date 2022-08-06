package com.application.godzilla.service;

import com.application.godzilla.exception.type.BusinessException;
import com.application.godzilla.model.User;
import com.application.godzilla.repository.UserRepository;
import com.application.godzilla.resources.AbstractService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class UserService extends AbstractService<User> {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public JpaRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public void validateCreateOrUpdate(User entity) {
        if (ObjectUtils.isEmpty(entity.getUsername())) {
            throw new BusinessException("Usuário não pode estar com Nome vazio.");
        }

        if (ObjectUtils.isEmpty(entity.getEmail())) {
            throw new BusinessException("Usuário não pode estar com E-mail vazio.");
        }

        if (ObjectUtils.isEmpty(entity.getPassword())) {
            throw new BusinessException("Usuário não pode estar com Senha vazia.");
        }

        try {
            InternetAddress emailAddr = new InternetAddress(entity.getEmail());
            emailAddr.validate();
        } catch (AddressException e) {
            throw new BusinessException("E-mail inválido.");
        }

        if (userRepository.countByEmailIgnoreCaseAndIdNotLike(entity.getEmail(), ObjectUtils.isEmpty(entity.getId()) ? 0 : entity.getId()) != 0L) {
            throw new BusinessException("Já existe usuário com email:".concat(entity.getEmail()).concat(" cadastrado."));
        }

        this.encodePassword(entity);
    }

    private void encodePassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }

}
