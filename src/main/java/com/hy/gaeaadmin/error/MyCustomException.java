/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.error;

/**
 *
 * @author hendryyu
 */
public class MyCustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String errCode;
    private String errMessage;
    public MyCustomException(String errCode, String errMessage) {
        super();
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
    
}
