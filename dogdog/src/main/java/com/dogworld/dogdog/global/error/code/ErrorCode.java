package com.dogworld.dogdog.global.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // == Member ==
    USER_NOT_FOUND("존재하지 않는 멤버입니다.", HttpStatus.NOT_FOUND),
    DUPLICATED_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    DUPLICATED_USERNAME("이미 사용 중인 아이디입니다.", HttpStatus.CONFLICT),

    // == Product ==
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND),
    DUPLICATED_PRODUCT_NAME("상품 이름이 중복입니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_CANNOT_BE_DELETED("완료된 주문에 포함된 상품은 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_PRODUCT_STOCK("해당 상품의 재고({stock})가 요청 수량({quantity})보다 부족합니다.", HttpStatus.BAD_REQUEST),

    // == Category ==
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PARENT_CATEGORY_NOT_FOUND("상위 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_SET_SELF_AS_PARENT_CATEGORY("자기 자신을 부모 카테고리로 지정할 수 없습니다.", HttpStatus.BAD_REQUEST),
    CATEGORY_HAS_CHILDREN("하위 카테고리가 존재하여 삭제할 수 없습니다.", HttpStatus.CONFLICT),
    CATEGORY_HAS_PRODUCTS("카테고리에 상품이 존재하여 삭제할 수 없습니다.", HttpStatus.CONFLICT),

    // == Cart ==
    CART_NOT_FOUND("장바구니가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_CART_ITEM_FOR_DELETE("삭제하는 아이템이 장바구니에 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    CART_ITEM_NOT_BELONG_TO_MEMBER("회원의 장바구니에 속하지 않은 아이템입니다.", HttpStatus.BAD_REQUEST),
    CART_NOT_BELONG_TO_MEMBER("회원의 장바구니가 아닙니다.", HttpStatus.BAD_REQUEST),
    EMPTY_CART("장바구니에 담긴 상품이 없습니다.", HttpStatus.BAD_REQUEST),

    // == validation error 공통화 ==
    INVALID_INPUT_VALUE("잘못된 입력입니다.", HttpStatus.BAD_REQUEST)
    ;

    private final String message;
    private final HttpStatus httpStatus;

    public String getCode() {
        return name();
    }
}