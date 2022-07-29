package com.application.godzilla.service;

import com.application.godzilla.exception.type.BusinessException;
import com.application.godzilla.exception.type.InternalException;
import com.application.godzilla.exception.type.NotFoundException;
import com.application.godzilla.model.User;
import com.application.godzilla.model.dto.UserDto;
import com.application.godzilla.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public UserDto create(User user) {
        validation(user);
        validationCreate(user);
        encodePassword(user);
        try {
            return modelMapper.map(userRepository.save(user), UserDto.class);
        } catch (Exception exception) {
            throw new InternalException("Erro ao inserir usuário!");
        }
    }

    @Transactional
    public UserDto update(Long id, User user) {
        User userBD = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado na base de dados!"));
        updatePassword(user, userBD);
        validation(user);
        validationUpdate(user);
        try {
            return modelMapper.map(userRepository.save(user), UserDto.class);
        } catch (Exception exception) {
            throw new InternalException("Erro ao atualizar usuário!");
        }
    }

    private void updatePassword(User user, User userBD) {
        if (ObjectUtils.isEmpty(user.getPassword())) {
            user.setPassword(userBD.getPassword());
        } else {
            encodePassword(user);
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

    public UserDto findById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado na base de dados!")), UserDto.class);
    }

    private void encodePassword(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!ObjectUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    private void validation(User user) {
        if (ObjectUtils.isEmpty(user.getUsername())) {
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

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
