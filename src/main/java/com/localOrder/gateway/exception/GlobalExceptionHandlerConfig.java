package com.localOrder.gateway.exception;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GlobalExceptionHandlerConfig {

    @Bean
    @Order(-2)
    public WebExceptionHandler globalExceptionHandler(List<ViewResolver> viewResolvers) {
        return (exchange, ex) -> {
            if (ex instanceof GatewayException) {
                return handleGatewayException(exchange, (GatewayException) ex);
            } else if (ex instanceof ResponseStatusException) {
                return handleResponseStatusException(exchange, (ResponseStatusException) ex);
            }
            return Mono.error(ex);
        };
    }

    private Mono<Void> handleGatewayException(ServerWebExchange exchange, GatewayException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> handleResponseStatusException(ServerWebExchange exchange, ResponseStatusException ex) {
        exchange.getResponse().setStatusCode(ex.getStatusCode());
        return exchange.getResponse().setComplete();
    }
}
