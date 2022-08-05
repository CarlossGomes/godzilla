package com.application.godzilla.service;

import com.application.godzilla.exception.type.BusinessException;
import com.application.godzilla.model.Cliente;
import com.application.godzilla.repository.ClienteRepository;
import com.application.godzilla.resources.AbstractService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ClienteService extends AbstractService<Cliente> {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return clienteRepository;
    }

    @Override
    public void validateCreateOrUpdate(Cliente entity) {
        if (ObjectUtils.isEmpty(StringUtils.trimAllWhitespace(entity.getNome()))) {
            throw new BusinessException("Cliente não pode ter campo nome vazio.");
        }

        if (ObjectUtils.isEmpty(StringUtils.trimAllWhitespace(entity.getTelefone()))) {
            throw new BusinessException("Cliente não pode ter campo telefone vazio.");
        }

        if (ObjectUtils.isEmpty(StringUtils.trimAllWhitespace(entity.getCPF_CNPJ()))) {
            throw new BusinessException("Cliente não pode ter campo CPF/CNPJ vazio.");
        }

    }
}
