package com.example.demo.service;

import com.example.demo.dto.request.ClienteRequestDTO;
import com.example.demo.dto.response.ClienteResponseDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.exception.ClienteNotFoundException;
import com.example.demo.exception.EmailJaCadastradoException;
import com.example.demo.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new EmailJaCadastradoException(dto.email());
        }

        Cliente cliente = Cliente.builder()
                .nome(dto.nome())
                .email(dto.email())
                .telefone(dto.telefone())
                .build();

        return toDTO(repository.save(cliente));
    }

    public Page<ClienteResponseDTO> listar(String nome, Pageable pageable) {
        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(nome, pageable).map(this::toDTO);
        }
        return repository.findAll(pageable).map(this::toDTO);
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id)));
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        if (repository.existsByEmailAndIdNot(dto.email(), id)) {
            throw new EmailJaCadastradoException(dto.email());
        }

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());

        return toDTO(repository.save(cliente));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ClienteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private ClienteResponseDTO toDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }
}
