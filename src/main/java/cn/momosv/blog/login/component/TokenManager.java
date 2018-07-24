package cn.momosv.blog.login.component;

import cn.momosv.blog.login.model.UserInfoPO;

public interface TokenManager {

    /**
     * 创建token
     * @param userInfo
     * @return
     */
    String getToken(UserInfoPO userInfo);

    /**
     * 刷新用户
     * @param token
     */
    void refreshUserToken(String token);

    /**
     * 用户退出登陆
     * @param token
     */
    void loginOff(String token);

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    UserInfoPO getUserInfoByToken(String token);

}
