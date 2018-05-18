package org.garen.pitaya.mybatis.domain;

import lombok.Data;

@Data
public class LoginDTO {
	private String code;
	private String message;
	private Object data;
}