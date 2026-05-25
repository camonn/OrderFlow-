package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
