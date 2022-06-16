package br.com.alurafood.microservicesalura.framework.configuration;

import br.com.alurafood.microservicesalura.MicroservicesAluraApplication;
import br.com.alurafood.microservicesalura.application.ports.out.PagamentoRepository;
import br.com.alurafood.microservicesalura.application.ports.out.PedidoOpenFeign;
import br.com.alurafood.microservicesalura.application.service.PagamentoUseCaseImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MicroservicesAluraApplication.class)
public class SeviceConfig {

    @Bean
    public PagamentoUseCaseImpl pagamentoUseCaseImpl(PagamentoRepository pagamentoRepository, ModelMapper modelMapper, PedidoOpenFeign pedidoOpenFeign) {
        return new PagamentoUseCaseImpl(pagamentoRepository, modelMapper, pedidoOpenFeign);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
