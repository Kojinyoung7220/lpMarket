package com.lpMarket.domain;

import com.lpMarket.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    private String name;
    private int price;
    private int stockQuantity;

    private String Genre;
    private String era;
    private String artist;


    //비즈니스 로직//
    public void addStock(int add) {
        stockQuantity += add;
    }

    public void removeStock(int lose) {
        int restStock = stockQuantity - lose;       //stockQuantity가 음수가 되는 걸 방지..!
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock", stockQuantity);
        }
        stockQuantity = restStock;
    }

    @Builder
    public Item(String name, int price, int stockQuantity, String genre, String era, String artist) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.Genre = genre;
        this.era = era;
        this.artist = artist;
    }

    public void changeItem(String name, int price, int stockQuantity, String genre, String era, String artist) {
        this.name =name;
        this.price =price;
        this.stockQuantity =stockQuantity;
        this.Genre =genre;
        this.era =era;
        this.artist =artist;
}
}
