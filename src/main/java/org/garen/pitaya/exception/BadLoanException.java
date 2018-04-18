package org.garen.pitaya.exception;

/**
 * <B>文件名称：</B><BR>
 * <B>文件描述：</B>业务异常<BR>
 * <BR>
 * <B>版权声明：</B>(C)2016-2018<BR>
 * <B>公司部门：</B>东方银谷 研发二部 CBG<BR>
 * <B>创建时间：</B>16/12/9<BR>
 *
 * @author 李云龙  liyunlong@yingu.com
 * @version 1.0
 **/


public class BadLoanException extends Exception{


    public BadLoanException(String message) {
        super(message);
    }

    public BadLoanException(final int code, String message) {
        super(message);
    }
}
