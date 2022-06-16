package br.com.alurafood.microservicesalura.framework.adapters.out.client;

import br.com.alurafood.microservicesalura.application.ports.out.PedidoOpenFeign;
import org.springframework.stereotype.Component;

@Component
public class PedidoOpenFeignImpl implements PedidoOpenFeign {

    private final PedidoClient pedidoClient;

    public PedidoOpenFeignImpl(PedidoClient pedidoClient) {
        this.pedidoClient = pedidoClient;
    }

    @Override
    public void updatePayment(Long id) {
        pedidoClient.updatePayment(id);
    }
}
