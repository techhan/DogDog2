package com.dogworld.dogdog.cart.interfaces;

import com.dogworld.dogdog.cart.application.CartCommandService;
import com.dogworld.dogdog.cart.application.CartQueryService;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartRequest;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartItemAddResponse;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartResponse;
import com.dogworld.dogdog.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

  private final CartCommandService cartCommandService;
  private final CartQueryService cartQueryService;

  @PostMapping("/items")
  public ResponseEntity<ApiResponse<List<CartItemAddResponse>>> addItems(@Valid @RequestBody CartRequest cartRequest) {
    List<CartItemAddResponse> response = cartCommandService.addItems(cartRequest);
    return ResponseEntity.ok(ApiResponse.success(response));
  }


}
