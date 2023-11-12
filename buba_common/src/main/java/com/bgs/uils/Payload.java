package com.bgs.uils;

import lombok.Data;

import java.util.Date;

/**
 * 封装载荷信息
 *  token 中的用户信息，将token 中载荷部分单独封装为一个对象
 * @param <T>
 */
@Data
public class Payload<T> {
    private String id; // 编号
    private T userInfo; // 对象信息
    private Date expiration; // 过期时间
}
