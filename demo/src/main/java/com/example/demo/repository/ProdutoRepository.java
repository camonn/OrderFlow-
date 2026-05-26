package com.example.demo.repository;

import com.example.demo.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Page<Produto> findByNameContainingIgnoreCase(String name, Pageable pageable);
}