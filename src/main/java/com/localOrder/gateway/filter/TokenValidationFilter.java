package com.localOrder.gateway.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.localOrder.gateway.exception.GatewayException;
import com.localOrder.gateway.util.JwtUtil;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Component
public class TokenValidationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		
				if(!routeValidator.isSecured.test(request)) {
			if(request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				
				String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
				
				if(authHeader != null && authHeader.startsWith("Bearer ")) {
					
					final String token = authHeader.substring(7);
					
					try {
						jwtUtil.validateToken(token);
					}catch(Exception e) {
						throw new GatewayException("401 : Unauthorized");
					}
				}else {
					throw new GatewayException("401 : Unauthorized");
				}
			}else {
				throw new GatewayException("401 : Unauthorized");
			}
		}
		
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -2;
	}
}
