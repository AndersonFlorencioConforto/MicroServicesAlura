package br.com.alurafood.microservicesalura.application.service;

import br.com.alurafood.microservicesalura.application.ports.in.PagamentoUseCase;
import br.com.alurafood.microservicesalura.application.ports.out.PagamentoRepository;
import br.com.alurafood.microservicesalura.application.ports.out.PedidoOpenFeign;
import br.com.alurafood.microservicesalura.domain.dtos.PagamentoDTO;
import br.com.alurafood.microservicesalura.domain.models.Pagamento;
import br.com.alurafood.microservicesalura.domain.models.enums.Status;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

public class PagamentoUseCaseImpl implements PagamentoUseCase {

    private final PagamentoRepository pagamentoRepository;
    private final ModelMapper modelMapper;
    private final PedidoOpenFeign pedidoOpenFeign;

    public PagamentoUseCaseImpl(PagamentoRepository pagamentoRepository, ModelMapper modelMapper, PedidoOpenFeign pedidoOpenFeign) {
        this.pagamentoRepository = pagamentoRepository;
        this.modelMapper = modelMapper;
        this.pedidoOpenFeign = pedidoOpenFeign;
    }

    @Override
    public Page<PagamentoDTO> findAllPagamentos(Pageable pageable) {
        return pagamentoRepository.findAllPagamento(pageable)
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDTO.class));
    }

    @Override
    public PagamentoDTO findById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não existente"));
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }


    @Override
    public PagamentoDTO save(PagamentoDTO pagamentoDTO) {
        pagamentoDTO.setStatus(Status.CRIADO);
        var pagamento = new Pagamento();
        BeanUtils.copyProperties(pagamentoDTO, pagamento);
        pagamentoRepository.save(pagamento);
        return pagamentoDTO;
    }

    @Override
    public PagamentoDTO update(PagamentoDTO pagamentoDTO, Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não existente"));
        BeanUtils.copyProperties(pagamentoDTO, pagamento);
        pagamentoRepository.save(pagamento);
        return pagamentoDTO;
    }

    @Override
    public void delete(Long id) {
        try {
            pagamentoRepository.delete(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Id not found");
        }
    }

    @Override
    public void confirmarPagamento(Long id) {
        try {
            Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não existente"));
            pagamento.setStatus(Status.CONFIRMADO);
            pagamentoRepository.save(pagamento);
            pedidoOpenFeign.updatePayment(pagamento.getPedidoId());
        } catch (FeignException e) {
            throw new EntityNotFoundException("ID DO PEDIDO NÃO ENCONTRADO");
        }
    }

    @Override
    public void alteraStatus(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não existente"));
        pagamento.setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        pagamentoRepository.save(pagamento);
    }
}
