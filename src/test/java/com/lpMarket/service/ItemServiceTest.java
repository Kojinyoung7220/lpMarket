package com.lpMarket.service;

import com.lpMarket.domain.Item;
import com.lpMarket.exception.NotEnoughStockException;
import com.lpMarket.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;


    @Test
    public void item_저장_및_조회_성공(){
        //given
        Item item = Item.builder()
                .name("kendrick lamar")
                .build();
        //when
        Long itemId = itemService.saveItem(item);
        Item findItem = itemService.findOne(itemId);


        //then
        assertNotNull(itemId); // ID가 생성되었는지 확인
        assertEquals(item.getName(), findItem.getName());   //서비스단에서 부터 찾는 것을 조회
        assertEquals(item, itemRepository.findOne(itemId));  // DB에서 조회한 객체와의 비교
    }

    @Test
    void item_개수_증가(){
        //given
        Item item = Item.builder()
                .name("kendrick lamar")
                .stockQuantity(0)
                .build();

        //when
        item.addStock(1);

        //then
        assertEquals(1, item.getStockQuantity());
    }

    @Test
    void item_수량_없음() throws NotEnoughStockException {
        //given
        Item item = new Item();

        //expected
        assertThrows(NotEnoughStockException.class, () -> item.removeStock(1));
    }
}