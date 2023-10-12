package com.khaled.orderservice.service;

import com.khaled.orderservice.dto.InventoryResponse;
import com.khaled.orderservice.dto.OrderLineItemsDto;
import com.khaled.orderservice.dto.OrderRequest;
import com.khaled.orderservice.model.Order;
import com.khaled.orderservice.model.OrderLineItems;
import com.khaled.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        
        List<OrderLineItems> orderLineItemsList=orderRequest.getOrderLineItemsDtos().stream().map(this::maptoDto).toList();

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes=order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode)
        .toList();
        InventoryResponse[] inventoryResponses= webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
               uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block();

        boolean result=Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
       if(result) {
           orderRepository.save(order);
       }else{
           throw new IllegalArgumentException("Product is out of stock, please try again later");
       }
    }

    private OrderLineItems maptoDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems=new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
