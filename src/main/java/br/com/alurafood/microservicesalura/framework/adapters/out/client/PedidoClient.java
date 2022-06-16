package br.com.alurafood.microservicesalura.framework.adapters.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("ms-pedidos")
public interface PedidoClient {

    @PutMapping(value = "/pedidos/{id}/pago")
    void updatePayment(@PathVariable Long id);
}
