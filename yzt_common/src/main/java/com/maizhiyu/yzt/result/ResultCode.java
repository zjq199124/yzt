package com.maizhiyu.yzt.result;

/**
 * API 统一返回状态码
 */
public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(0, "成功"),
    FAILURE(-1, "失败"),

    /* 令牌失效 */
    SERVER_ERROR(400, "服务器错误"),
    TOKEN_INVALID(401,"令牌失效"),
    SERVER_ERROR_403(403, "该接口禁止访问"),
    SERVER_ERROR_404(404, "接口地址无效"),
    SERVER_ERROR_405(405, "该资源禁止访问"),
    SERVER_ERROR_408(408, "请求超时"),
    SERVER_ERROR_500(500, "系统繁忙，请稍后重试"),

    /*参数错误 10001-19999 */

    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),
    PARAM_IS_BLANK_DOCID(10005, "docId参数为空"),
    DATE_IS_INVALID(10006, "日期参数无效"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或@Transactional(rollbackFor=Exception.class)"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    Cert_HAS_EXISTED(20006, "认证已存在"),
    USER_IS_ADMIN(20007, "不可以删除管理员"),
    USER_OLD_PASS_ERROR(20008, "旧密码错误"),
    USER_PASS_ERROR(20009, "密码错误"),
    USER_CODE_NULL(20010, "验证码已过期"),
    USER_CODE_ERROR(20011, "验证码错误"),
    USER_CODE_SMS_FAIL(20012, "验证码发送失败"),
    USER_PHONE_FAIL(20013, "当前手机号不是原注册号"),
    USERNAME_OR_PASSWORD_ERROR(20014, "用户名或密码错误"),
    OPENID_ERROR(20015, "openId错误!"),


    /* 业务错误：30001-39999 */
    CREATE_FAIL(30001, "创建失败"),
    ALREADY_HAVE_QUEST(30002, "已有录入的问卷，不可以进行该操作"),
    ALREADY_SUB_EXAMINE(30003, "数据已提交审核，不可以进行删除"),
    ALREADY_START_EXAMINE(30003, "数据已提交审核，不可以进行撤回"),
    ALREADY_EXAMINE_OTHER(30004, "其他人正在进行审核，不可以进行操作"),
    QUEST_NOT_FINISH(30005, "问卷未完成"),
    ALREADY_EXAMINE_FINISH(30006, "改问卷已审核完成，不可以进行操作"),
    ALREADY_PURCHASE(30007, "处方已购买"),
    ALREADY_REGISTER(30008, "已有挂号"),
    ALREADY_INQUIRY(30009, "已有问诊"),
    ALREADY_RELATION_DATA(30010, "已有关联数据，不能删除"),
    ALREADY_RECEPTION(30011, "医生已接诊，不能取消"),
    ALREADY_OVERSTEP(30012, "放号数量超出结束时间"),
    ALREADY_SUCCESS(30013, "咨询已完成，不能取消"),
    ALREADY_REFUND(30014, "该咨询已取消"),
    ALREADY_EXPRIED_REGISTER(30015, "挂号已满"),
    INSUFFICIENT_BALANCE(30016, "余额不足"),
    INSUFFICIENT_STOCK(30017, "库存不足"),
    ALREADY_DELIVERY(30018, "已配送，不能撤销"),
    NOT_PACK(30019, "未完成，不能配送"),
    NOT_REFUND(30020, "不能退费"),
    NOT_REMEDY(30021, "剩余次数为0"),
    HAVE_REMAINDER(30022, "还有余号"),
    NOT_CONSULT(30023, "医生未开通在线咨询"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "只有标签 Owner ,才具备删除权限"),
    PERMISSION_NO_PHONE_ACCESS(70002,"此认证标签已有员工认证，不可以进行删除"),
    PERMISSION_NO_SUPER_ADMIN(70003,"超级管理员没有录入权限"),
    PERMISSION_NO_ENOUGH(70004,"无权限");


    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}
