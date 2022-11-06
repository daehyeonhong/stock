package com.example.stock.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long productId;
    Long quantity;
    @Version
    Long version;

    public Stock(final Long productId, final Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void decrease(final Long quantity) {
        if (this.quantity < quantity) throw new RuntimeException();
        this.quantity -= quantity;
    }
}
