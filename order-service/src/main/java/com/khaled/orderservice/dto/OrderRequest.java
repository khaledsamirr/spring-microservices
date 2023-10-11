package com.khaled.orderservice.dto;


import com.khaled.orderservice.model.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequest {
    private List<OrderLineItemsDto> orderLineItemsDtos;

}
