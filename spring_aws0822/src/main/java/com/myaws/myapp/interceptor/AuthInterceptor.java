package com.myaws.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 가상경로에 해당 메소드 접근 전에 가로채기

		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") == null) {
			//로그인이 안되어있으면 이동하려고 하는 주소를 보관하고 
			//로그인 페이지로 보낸다.
			saveUrl(request); // 이동할 경로를 저장한다.
			
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.aws");
			return false;
		}else {
		return true;
		}
	}


	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}
	
	
	
	
	//사용자가 특정 페이지를 방문할 때 그 페이지의 URL을 세션에 저장하는 기능
	public void saveUrl(HttpServletRequest request) {
		
		String uri = request.getRequestURI();  // 전체경로 주소  
		String param = request.getQueryString(); // 파라미터를 가져온다. 
		
		// 파라미터가 없는 경우 빈 문자열로 설정, 있으면 ?를 추가해서 완전한 쿼리 문자열로 만든다.
		if(param == null || param.equals("null") || param.equals("")) {
			param = "";
		}else {
			param = "?" +param;
		}
		
		// 최종적으로 이동할 페이지
		String locationUrl = uri+param;
		
		HttpSession session = request.getSession(); // 세션 객체 가져온다.
		if(request.getMethod().equals("GET")) { // 대문자 GET
			session.setAttribute("saveUrl",locationUrl);
		}
	}


}



