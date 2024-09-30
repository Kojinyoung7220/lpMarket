package com.lpMarket.service;

import com.lpMarket.domain.Item;
import com.lpMarket.repository.ItemRepository;
import com.lpMarket.service.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {


    private final ItemRepository itemRepository;

    public Long saveItem(Item item){
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

    public void itemUpdate(UpdateItemDto form){
        Item item = itemRepository.findOne(form.getId());
        item.changeItem(form.getName(), form.getPrice(),form.getStockQuantity(), form.getGenre(),form.getEra(),form.getArtist());
//        itemRepository.save(item); // 변경감지가 일어나나? -> 일어난다~~o
     }





}
