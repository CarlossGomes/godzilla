package com.application.godzilla.controller;

import com.application.godzilla.model.Produto;
import com.application.godzilla.resources.AbstractCrudController;
import com.application.godzilla.resources.AbstractService;
import com.application.godzilla.service.ProdutoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController extends AbstractCrudController<Produto> {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public AbstractService getService() {
        return produtoService;
    }
}
