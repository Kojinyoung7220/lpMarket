package com.lpMarket.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

@Embeddable
@Getter
public class Address {      //값 타입은 변경 불가능하게 설정

    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }

    @Builder
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
