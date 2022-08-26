package com.application.godzilla.controller;

import com.application.godzilla.model.Cliente;
import com.application.godzilla.model.User;
import com.application.godzilla.resources.AbstractCrudController;
import com.application.godzilla.resources.AbstractService;
import com.application.godzilla.service.ClienteService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController extends AbstractCrudController<Cliente> {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public AbstractService getService() {
        return this.clienteService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findAll(Pageable pageable, Cliente filter) {
        return ResponseEntity.ok(this.clienteService.read(pageable, filter));
    }
}
