package com.keevor.service;

import com.keevor.domain.R;
import com.keevor.domain.User;

public interface LoginService {
    /**
     * 登录功能
     * @param user
     * @return
     */
    R<String> login(User user);

    /**
     * 退出登录
     * @return
     */
    R<String> logout();
}
