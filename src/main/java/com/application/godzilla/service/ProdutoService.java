package com.application.godzilla.service;

import com.application.godzilla.exception.type.BusinessException;
import com.application.godzilla.model.Produto;
import com.application.godzilla.repository.ProdutoRepository;
import com.application.godzilla.resources.AbstractService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ProdutoService extends AbstractService<Produto> {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return produtoRepository;
    }

    @Override
    public void validateCreateOrUpdate(Produto entity) {
        if (ObjectUtils.isEmpty(entity.getDescricao())) {
            throw new BusinessException("Produto não pode estar com descrição vazia.");
        }

        if (ObjectUtils.isEmpty(entity.getValor())) {
            throw new BusinessException("Produto não pode estar com valor vazio.");
        }

        if (ObjectUtils.isEmpty(entity.getMargem())) {
            throw new BusinessException("Produto não pode estar com margem vazia.");
        }

        if (ObjectUtils.isEmpty(entity.getQuantidade())) {
            throw new BusinessException("Produto não pode estar com quantidade vazia.");
        }

    }
}
