package com.application.godzilla.service;

import com.application.godzilla.exception.type.BusinessException;
import com.application.godzilla.exception.type.InternalException;
import com.application.godzilla.exception.type.NotFoundException;
import com.application.godzilla.model.User;
import com.application.godzilla.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User create(User user) {
        validation(user);
        validationCreate(user);
        try {
            return userRepository.save(user);
        } catch (Exception exception) {
            throw new InternalException("Erro ao inserir usuário!");
        }
    }

    @Transactional
    public User update(User user) {
        validation(user);
        validationUpdate(user);
        try {
            return userRepository.save(user);
        } catch (Exception exception) {
            throw new InternalException("Erro ao atualizar usuário!");
        }
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado na base de dados!"));
        try {
            userRepository.deleteById(id);
        } catch (Exception exception) {
            throw new InternalException("Erro ao deletar usuário!");
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado na base de dados!"));
    }

    private void validation(User user) {
        if (ObjectUtils.isEmpty(user.getName())) {
            throw new BusinessException("Usuário não pode estar com Nome vazio!");
        }

        if (ObjectUtils.isEmpty(user.getEmail())) {
            throw new BusinessException("Usuário não pode estar com E-mail vazio!");
        }

        if (ObjectUtils.isEmpty(user.getPassword())) {
            throw new BusinessException("Usuário não pode estar com Senha vazia!");
        }
    }

    private void validationCreate(User user) {
        if (userRepository.countByEmail(user.getEmail()) != 0L) {
            throw new BusinessException("Já existe usuário com email:".concat(user.getEmail()).concat(" cadastrado!"));
        }
    }

    private void validationUpdate(User user) {
        if (userRepository.countByEmailAndIdNotLike(user.getEmail(), user.getId()) != 0L) {
            throw new BusinessException("Já existe usuário com email:".concat(user.getEmail()).concat(" cadastrado!"));
        }
    }
}
