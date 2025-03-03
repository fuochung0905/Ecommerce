package com.utc2.it.Ecommerce.Base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BaseDto<T> {
    private boolean isSuccess;
    private String message;
    private T data;
}
