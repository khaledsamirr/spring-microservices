package com.khaled.inventoryservice.service;

import com.khaled.inventoryservice.dto.InventoryResponse;
import com.khaled.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;



    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode){
        log.info("Wait Started");
        Thread.sleep(1000);
        log.info("Wait Ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
            InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity()>0).build()
        ).toList();
    }
}
