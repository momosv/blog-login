package cn.momosv.blog.login.baseController;


import cn.momosv.blog.login.component.impl.AuthManager;
import cn.momosv.blog.login.model.UserInfoPO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.message.AuthException;

public class BaseController {
    @Autowired
    public AuthManager authManager;

    public UserInfoPO getUser() throws AuthException {
      return  authManager.getUserInfo();
    }
}
