package com.example.demo1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    @NotNull(message = "手机号不能为空")//标记属性部位null
    @NotEmpty(message = "手机不能为空")//不能为""
    @NotBlank(message = "手机号不能为空")//不能为" "
    private String phone;

    @NotNull(message = "密码不能为空")//标记属性部位null
    @NotEmpty(message = "密码不能为空")//不能为""
    @NotBlank(message = "密码号不能为空")//不能为" "
    private String password;

    @NotNull(message = "验证码不能为空")//标记属性部位null
    @NotEmpty(message = "验证码不能为空")//不能为""
    @NotBlank(message = "验证码不能为空")//不能为" "
    private String code;
}