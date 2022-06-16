package br.com.alurafood.microservicesalura.framework.adapters.out.persistence;

import br.com.alurafood.microservicesalura.domain.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepositoryJpa extends JpaRepository<Pagamento,Long> {
}
