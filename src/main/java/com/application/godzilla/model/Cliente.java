package com.application.godzilla.model;

import com.application.godzilla.resources.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Data
@SQLDelete(sql = "UPDATE clientes SET deletado = CURRENT_TIMESTAMP WHERE cli_id = ?")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clientes")
public class Cliente extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cli_seq")
    @SequenceGenerator(name = "cli_seq", allocationSize = 1, sequenceName = "cli_seq")
    @Column(name = "cli_id")
    private Long id;

    @Column(name = "cli_nome")
    private String nome;

    @Column(name = "cli_telefone")
    private String telefone;

    @Column(name = "cli_email")
    private String email;

    @Column(name = "cli_cpf_cnpj")
    private String CPF_CNPJ;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<OrdemServico> ordemServicos;

}
