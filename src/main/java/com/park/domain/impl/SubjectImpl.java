package com.park.domain.impl;

import com.park.annotation.AaronCache;
import com.park.domain.ISubject;

/**
 * @author Aaron
 * @date 2020/6/6 15:45
 */
public class SubjectImpl implements ISubject {

	@AaronCache
	@Override
	public void doService() {
		System.out.println("My name is Aaron");
	}
}
