package com.example.demo.controller;

import com.example.demo.dto.request.AtualizarStatusPedidoRequestDTO;
import com.example.demo.dto.request.ItemPedidoRequestDTO;
import com.example.demo.dto.response.ItemPedidoResponseDTO;
import com.example.demo.dto.response.PedidoResponseDTO;
import com.example.demo.entity.ItemPedido;
import com.example.demo.entity.Pedido;
import com.example.demo.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos e itens")
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(summary = "Criar pedido para um cliente")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestParam Long clienteId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDTO(pedidoService.criarPedido(clienteId)));
    }

    @Operation(summary = "Listar todos os pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPedidos()
                .stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(pedidoService.buscarPorId(id)));
    }

    @Operation(summary = "Adicionar item ao pedido")
    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<PedidoResponseDTO> adicionarItem(
            @PathVariable Long pedidoId,
            @RequestBody @Valid ItemPedidoRequestDTO dto
    ) {
        return ResponseEntity.ok(toDTO(pedidoService.adicionarItem(pedidoId, dto)));
    }

    @Operation(summary = "Remover item do pedido")
    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<PedidoResponseDTO> removerItem(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId
    ) {
        return ResponseEntity.ok(toDTO(pedidoService.removerItem(pedidoId, itemId)));
    }

    @Operation(summary = "Atualizar status do pedido")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusPedidoRequestDTO dto
    ) {
        return ResponseEntity.ok(toDTO(pedidoService.atualizarStatus(id, dto)));
    }

    private PedidoResponseDTO toDTO(Pedido pedido) {
        List<ItemPedidoResponseDTO> itens = pedido.getItens() == null
                ? List.of()
                : pedido.getItens().stream().map(this::toItemDTO).toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getData(),
                pedido.getStatus(),
                pedido.getTotalValue(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                itens
        );
    }

    private ItemPedidoResponseDTO toItemDTO(ItemPedido item) {
        return new ItemPedidoResponseDTO(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getName(),
                item.getAmount(),
                item.getSubtotal()
        );
    }
}
