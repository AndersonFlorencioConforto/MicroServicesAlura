package br.com.alurafood.microservicesalura.application.ports.in;

import br.com.alurafood.microservicesalura.domain.dtos.PagamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagamentoUseCase {

    Page<PagamentoDTO> findAllPagamentos(Pageable pageable);
    PagamentoDTO findById(Long id);
    PagamentoDTO save(PagamentoDTO pagamentoDTO);
    PagamentoDTO update(PagamentoDTO pagamentoDTO,Long id);
    void delete(Long id);
    void confirmarPagamento(Long id);
    void alteraStatus(Long id);
}
