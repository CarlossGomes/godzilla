package com.application.godzilla.model;

import com.application.godzilla.resources.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "produto_ordem_servico")
public class ProdutoOrdemServico extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prdtos_seq")
    @SequenceGenerator(name = "prdtos_seq", allocationSize = 1, sequenceName = "prdtos_seq")
    @Column(name = "prdtos_id")
    private Long id;

    @Column(name = "prdtos_descricao")
    private String descricao;

    @Column(name = "prdtos_quantidade")
    private Long quantidade;

    @Column(name = "prdtos_valor")
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prdtos_os_id")
    @JsonBackReference
    private OrdemServico ordemServico;

}
