package io.github.yaowenbin.dbai.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 3452913614867544640L;

    private T data;

    private int code;

    private String msg;

    public R() {
    }

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private R(int code, String msg) {
        this(code, msg, null);
    }


    public static <T> R<T> success(T data) {
        return new R<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> R<T> success() {
        return success((T) null);
    }



    public static <T> R<T> failed() {
        return failed(ResultCodeEnum.ERROR.getMessage());
    }

    public static <T> R<T> failed(String message) {
        return new R<>(ResultCodeEnum.ERROR.getCode(), message);
    }

    public enum ResultCodeEnum {
        // 数据操作错误定义
        SUCCESS(200, "success"),
        ERROR(500, "server internal error, please contact administrator"),



        /**
         * 600MySQL实例模块
         */
        MYSQL_CONNECTION_ERROR(601, "MySQL连接异常:"),
        DATASOUCE_NOT_FOUND(602, "数据源未找到"),

        ;
        private final int code;
        private final String message;

        ResultCodeEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }

}
