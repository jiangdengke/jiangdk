package com.jiang.pojo.vo.resp;

/**
 * @author by itheima
 * @Date 2021/12/21
 * @Description
 */
public enum ResponseCode{
    ERROR("操作失败"),
    SUCCESS("操作成功"),
    DATA_ERROR("参数异常"),
    NO_RESPONSE_DATA("无响应数据"),
    CHECK_CODE_NOT_EMPTY("验证码不能为空"),
    CHECK_CODE_TIMEOUT("验证码已过期,请刷新重试"),
    CHECK_CODE_ERROR("验证码错误"),
    USERNAME_OR_PASSWORD_ERROR("用户名或密码错误"),
    ACCOUNT_EXISTS_ERROR("该账号已存在"),
    ROLE_EXISTS_ERROR("该角色已存在"),
    ACCOUNT_NOT_EXISTS("该账号不存在"),
    TOKEN_ERROR("用户未登录，请先登录"),
    NOT_PERMISSION("没有权限访问该资源"),
    ANONMOUSE_NOT_PERMISSION("匿名用户没有权限访问"),
    AUTHENTICATION_FALSE("认证失败"),
    UNAUTHORIZED_Token("未认证的票据"),
    INVALID_TOKEN("无效的票据");
    private String message;

    ResponseCode( String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}