package com.dogworld.dogdog.cart.interfaces;

import com.dogworld.dogdog.cart.application.CartCommandService;
import com.dogworld.dogdog.cart.application.CartQueryService;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartAddRequest;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartRequest;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartItemAddResponse;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartResponse;
import com.dogworld.dogdog.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
  // TODO. memberId의 경우 추후 스프링 시큐리티 등 로그인 기능이 추가된 후에 변경 예정

  private final CartCommandService cartCommandService;
  private final CartQueryService cartQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<CartResponse>> getAllCartItems(@Valid @RequestBody CartRequest request) {
    CartResponse response = cartQueryService.getAllCartItems(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @PostMapping("/items")
  public ResponseEntity<ApiResponse<List<CartItemAddResponse>>> addItems(@Valid @RequestBody CartAddRequest cartRequest) {
    List<CartItemAddResponse> response = cartCommandService.addItems(cartRequest);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @DeleteMapping("/items/{cartItemId}")
  public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long cartItemId, @Valid @RequestBody CartRequest request) {
    cartCommandService.deleteItem(cartItemId, request);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @DeleteMapping("/{cartId}/items")
  public ResponseEntity<ApiResponse<Void>> deleteItems(@PathVariable Long cartId, @Valid @RequestBody CartRequest request) {
    cartCommandService.deleteItems(cartId, request);
    return ResponseEntity.ok(ApiResponse.success());
  }
}
