package com.myaws.myapp.aop;

import org.apache.catalina.tribes.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BoardTimeCheckAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	// ���۰� �� �ι��� üũ�ϴ� Around
	@Around("execution(* com.myaws.myapp.service.BoardService*.*(..))")
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		
		logger.info("�����ϴ� aop");
		logger.info("�Ű�����: " + Arrays.toString(pjp.getArgs()));
		long startTime = System.currentTimeMillis();
		
		// �ش�Ǵ� ��������Ʈ�� �����Ų��.
		result = pjp.proceed();
		
		long endTime = System.currentTimeMillis();
		logger.info("������ aop");
		
		// �ð��� �󸶳� �ɸ����� ��� 
		long durTime = endTime - startTime;
		logger.info(pjp.getSignature().getName()+"�ɸ��ð�: " + durTime); 
		
		return result;
		
		
	}

}
