package com.dogworld.dogdog.global.error.detail;

import java.util.Map;

public record StockExceptionDetail(int stock, int quantity) implements ErrorDetail {
  public Map<String, Object> toMap() {
    return Map.of(
        "stock", stock,
        "quantity", quantity
    );
  }
}
