package com.myaws.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
// @slf4j �Һ� ���̺귯�� �߰��� �� ���
public class SampleAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	//���� ���񽺿� �ִ� ��� �޼��忡 �ش�Ǵ� �͵��� ���� ���� �긦 ���ڴ�. 
	// ������ �޼ҵ带 ������ �� �갡 ��Ÿ������ �Ѵ�. 
	@Before("execution(* com.myaws.myapp.service.BoardService*.*(..))")
	public void startLog() {
		
		logger.info("----------------------");
		logger.info("aop �α� �׽�Ʈ���Դϴ�.");
		logger.info("----------------------");
		System.out.println("�׽�Ʈ");
	}

}
