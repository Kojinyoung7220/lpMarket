package com.lpMarket.controller;

import com.lpMarket.domain.Item;
import com.lpMarket.service.ItemService;
import com.lpMarket.web.request.CreateItemForm;
import com.lpMarket.web.request.ItemForm;
import com.lpMarket.service.dto.UpdateItemDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String itemSignIn(Model model){
        model.addAttribute("itemForm" , new CreateItemForm());
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String createItem(@Valid @ModelAttribute("itemForm") CreateItemForm itemForm, BindingResult result){

        if(result.hasErrors()){
            return "items/createItemForm";
        }

        Item item = FormToItem(itemForm);

        itemService.saveItem(item);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String ItemList(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemService.findOne(itemId);

        UpdateItemDto form = ItemToUpdateItemForm(item);

        model.addAttribute("itemForm", form);

        return "items/updateItemForm";
    }


    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@Valid @ModelAttribute("itemForm") ItemForm form, BindingResult result){ //아이템폼은 컨트롤러까지만 사용하자

        if(result.hasErrors()){
            return "items/updateItemForm";
        }

        itemService.itemUpdate(form.toUpdateItemDto()); // 원래 marge 생각도 안했다가 수정을하면 새로운 객체가 계속 생겨서 리파지토리에서 값을 가져와 그걸 수정하고 싶었었는데

        return "redirect:/items";

    }
    private Item FormToItem(CreateItemForm itemForm) {
        Item item = Item.builder()
                .name(itemForm.getName())
                .price(itemForm.getPrice())
                .stockQuantity(itemForm.getStockQuantity())
                .genre(itemForm.getGenre())
                .era(itemForm.getEra())
                .artist(itemForm.getArtist())
                .build();
        return item;
    }


    private UpdateItemDto ItemToUpdateItemForm(Item item) {
        UpdateItemDto form = UpdateItemDto.builder()      //update dto로 변경? -> 8/25 완료
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stockQuantity(item.getStockQuantity())
                .artist(item.getArtist())
                .era(item.getEra())
                .genre(item.getGenre())
                .build();
        return form;
    }
}
