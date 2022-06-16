package br.com.alurafood.microservicesalura.framework.adapters.out.persistence;

import br.com.alurafood.microservicesalura.application.ports.out.PagamentoRepository;
import br.com.alurafood.microservicesalura.domain.models.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final PagamentoRepositoryJpa pagamentoRepositoryJpa;

    public PagamentoRepositoryImpl(PagamentoRepositoryJpa pagamentoRepositoryJpa) {
        this.pagamentoRepositoryJpa = pagamentoRepositoryJpa;
    }

    @Override
    public Page<Pagamento> findAllPagamento(Pageable pageable) {
        return pagamentoRepositoryJpa.findAll(pageable);
    }

    @Override
    public Optional<Pagamento> findById(Long id) {
        return pagamentoRepositoryJpa.findById(id);
    }


    @Transactional
    @Override
    public Pagamento save(Pagamento pagamento) {
      return pagamentoRepositoryJpa.save(pagamento);
    }

    @Override
    public void delete(Long id) {
        pagamentoRepositoryJpa.deleteById(id);
    }

}
