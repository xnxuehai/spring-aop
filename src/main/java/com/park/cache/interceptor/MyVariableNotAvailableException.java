package com.park.cache.interceptor;

import org.springframework.expression.EvaluationException;

/**
 * @author Aaron
 * @date 2020/6/7 21:12
 */
public class MyVariableNotAvailableException extends EvaluationException {

	private final String name;


	public MyVariableNotAvailableException(String name) {
		super("Variable not available");
		this.name = name;
	}


	public final String getName() {
		return this.name;
	}

}
