package com.gilang.network.example.domain.payload.req;

import lombok.Data;

/**
 * @author gylang
 * data 2022/7/17
 */
@Data
public class RegisterReq {

    /** 用户名称 */
    private String username;

    /** 用户id */
    private Long password;

    /** 昵称 */
    private String nickname;



}
