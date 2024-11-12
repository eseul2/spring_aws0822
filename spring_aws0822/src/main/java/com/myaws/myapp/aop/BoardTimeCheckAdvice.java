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
	
	// 시작과 끝 두번을 체크하는 Around
	@Around("execution(* com.myaws.myapp.service.BoardService*.*(..))")
	public Object timelog(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		
		logger.info("시작하는 aop");
		logger.info("매개변수: " + Arrays.toString(pjp.getArgs()));
		long startTime = System.currentTimeMillis();
		
		// 해당되는 조인포인트를 진행시킨다.
		result = pjp.proceed();
		
		long endTime = System.currentTimeMillis();
		logger.info("끝나는 aop");
		
		// 시간이 얼마나 걸리는지 계산 
		long durTime = endTime - startTime;
		logger.info(pjp.getSignature().getName()+"걸린시간: " + durTime); 
		
		return result;
		
		
	}

}
