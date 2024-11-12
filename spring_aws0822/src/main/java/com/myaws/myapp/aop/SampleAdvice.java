package com.myaws.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
// @slf4j 롬복 라이브러리 추가할 때 사용
public class SampleAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	//보드 서비스에 있는 모든 메서드에 해당되는 것들을 들어가기 전에 얘를 찍어보겠다. 
	// 각각의 메소드를 실행할 때 얘가 나타나도록 한다. 
	@Before("execution(* com.myaws.myapp.service.BoardService*.*(..))")
	public void startLog() {
		
		logger.info("----------------------");
		logger.info("aop 로그 테스트중입니다.");
		logger.info("----------------------");
		System.out.println("테스트");
	}

}
