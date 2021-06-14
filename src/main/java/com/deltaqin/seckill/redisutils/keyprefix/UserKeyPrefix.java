package com.deltaqin.seckill.redisutils.keyprefix;

/**
 * @author deltaqin
 * @date 2021/6/14 上午10:13
 */
public class UserKeyPrefix extends BaseKeyPrefix{
    public static final int TOKEN_EXPIRE = 3600 *24*2;
    private static final int CODE_EXPIRE = 60 * 5;
    public static UserKeyPrefix getVerificationCode = new UserKeyPrefix("code", CODE_EXPIRE) ;
    public static UserKeyPrefix getToken = new UserKeyPrefix("tk", TOKEN_EXPIRE) ;
    public static UserKeyPrefix getByNickName = new UserKeyPrefix("nickName",0);

    public UserKeyPrefix(String prefix, int expireSeconds ) {
        super(prefix, expireSeconds);
    }

}
