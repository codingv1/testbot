package com.axess.history.exception;


/**
 *  JupiterException
 *  @author  Yao
 *
 */
public class JupiterException extends RuntimeException{

	private int code;

	public JupiterException() {
	}

	public JupiterException(int code, String message){
		super(message);
		this.code =code ;

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
