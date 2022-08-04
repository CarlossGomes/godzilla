package com.application.godzilla.controller;

import com.application.godzilla.model.OrdemServico;
import com.application.godzilla.resources.AbstractCrudController;
import com.application.godzilla.resources.AbstractService;
import com.application.godzilla.service.OrdemServicoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ordens-servicos")
public class OrdemServicoController extends AbstractCrudController<OrdemServico> {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @Override
    public AbstractService getService() {
        return ordemServicoService;
    }
}
