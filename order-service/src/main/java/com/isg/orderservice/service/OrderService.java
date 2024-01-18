package com.isg.orderservice.service;

import com.isg.orderservice.dto.OrderLineItemsDto;
import com.isg.orderservice.dto.OrderRequest;
import com.isg.orderservice.model.Order;
import com.isg.orderservice.model.OrderLineItems;
import com.isg.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapToDto).toList();

        order.setOrderLineItemsList(orderLineItems);

        // Call Inventory Service and place order if product is in stock
        // webClient make a synchronous request to given endpoint
        Boolean result = webClient.get().uri("http://localhost:8082/api/inventory")
                .retrieve().bodyToMono(Boolean.class).block();

        if(result){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItems.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
