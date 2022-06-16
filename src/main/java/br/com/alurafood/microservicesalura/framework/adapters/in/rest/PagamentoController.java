package br.com.alurafood.microservicesalura.framework.adapters.in.rest;

import br.com.alurafood.microservicesalura.application.ports.in.PagamentoUseCase;
import br.com.alurafood.microservicesalura.domain.dtos.PagamentoDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/pagamentos")
public class PagamentoController {

    private final PagamentoUseCase pagamentoUseCase;

    public PagamentoController(PagamentoUseCase pagamentoUseCase) {
        this.pagamentoUseCase = pagamentoUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoDTO>> findAllPagamentos(Pageable pageable) {
        return ResponseEntity.ok().body(pagamentoUseCase.findAllPagamentos(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(pagamentoUseCase.findById(id));
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> save(@RequestBody @Valid PagamentoDTO pagamentoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoUseCase.save(pagamentoDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PagamentoDTO> update(@PathVariable Long id,@RequestBody @Valid PagamentoDTO pagamentoDTO) {
        return ResponseEntity.ok().body(pagamentoUseCase.update(pagamentoDTO,id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pagamentoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @CircuitBreaker(name = "atualizaPedido",fallbackMethod = "pagamentoPendente")
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarPagamento(@PathVariable @NotNull Long id){
        pagamentoUseCase.confirmarPagamento(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> pagamentoPendente(Long id,Exception e){
        pagamentoUseCase.alteraStatus(id);
        return ResponseEntity.noContent().build();
    }
}
