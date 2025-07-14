package com.dogworld.dogdog.cart.domain;

import com.dogworld.dogdog.member.domain.Member;

import java.util.List;

public class TestCartFactory {

    public static Cart createCart(Member member, List<CartItem> items) {
        return Cart.create(member, items);
    }
}
