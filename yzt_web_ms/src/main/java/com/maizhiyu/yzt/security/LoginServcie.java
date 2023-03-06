package com.maizhiyu.yzt.security;

import com.maizhiyu.yzt.entity.MsUser;
import com.maizhiyu.yzt.result.Result;

/**
 * @author zjq
 * @date 2023-03-02
 */
public interface LoginServcie {
    Result login(MsUser user);
    Result logout() throws Exception;
}
