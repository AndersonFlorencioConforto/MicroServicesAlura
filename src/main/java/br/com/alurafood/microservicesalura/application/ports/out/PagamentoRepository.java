package br.com.alurafood.microservicesalura.application.ports.out;

import br.com.alurafood.microservicesalura.domain.models.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface PagamentoRepository {

    Page<Pagamento> findAllPagamento(Pageable pageable);
    Optional<Pagamento> findById(Long id);
    Pagamento save(Pagamento pagamento);
    void delete(Long id);
}
