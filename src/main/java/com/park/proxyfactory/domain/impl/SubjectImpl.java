package com.park.proxyfactory.domain.impl;

import com.park.proxyfactory.domain.ISubject;

/**
 * @author Aaron
 * @date 2020/6/6 15:45
 */
public class SubjectImpl implements ISubject {

	@Override
	public void doService() {
		System.out.println("My name is Aaron");
	}
}
