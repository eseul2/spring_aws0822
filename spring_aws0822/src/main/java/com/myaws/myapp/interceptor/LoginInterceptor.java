package com.myaws.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	
	// 로그인 후에 회원정보를 세션에 담는다.
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 가로채기 하기 전에 처리하는 메소드
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") != null) { // 널이 아니면
			session.removeAttribute("midx");
			session.removeAttribute("memberId");
			session.removeAttribute("memberName");
			session.invalidate();  //뭔가를 담기전에 전부 초기화 시킨다.
		}
		return true;
	}

	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
		// 컨트롤러에서 값을 담았기 때문에 이것을 꺼낸다는 의미
		// RedirectAttributes나 Model객체에 담은 값을 꺼낸다.
		String midx = modelAndView.getModel().get("midx").toString();
		String memberName = modelAndView.getModel().get("memberName").toString();
		String memberId = modelAndView.getModel().get("memberId").toString();
		
		modelAndView.getModel().clear();  // 넘어오는 파라미터 model값을 지운다 (노출되지 않게) 상단 주소에 보이지 않게 가리는거
		
		HttpSession session = request.getSession();  // 먼저 선언하기
		// 세션에 담는 과정
		if(midx != null) {  // midx가 널이 아니면 
			// 세션에 값을 담는다.
			session.setAttribute("midx", midx);
			session.setAttribute("memberId", memberId);
			session.setAttribute("memberName", memberName);
		}
	}


}





