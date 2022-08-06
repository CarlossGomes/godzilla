package com.application.godzilla.repository;

import com.application.godzilla.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Long countByIdNotLikeAndCpfcnpjIgnoreCase(Long id, String cpfcnpj);
}
