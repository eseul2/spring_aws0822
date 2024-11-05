package com.myaws.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	
	// �α��� �Ŀ� ȸ�������� ���ǿ� ��´�.
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// ����ä�� �ϱ� ���� ó���ϴ� �޼ҵ�
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") != null) { // ���� �ƴϸ�
			session.removeAttribute("midx");
			session.removeAttribute("memberId");
			session.removeAttribute("memberName");
			session.invalidate();  //������ ������� ���� �ʱ�ȭ ��Ų��.
		}
		return true;
	}

	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
		// ��Ʈ�ѷ����� ���� ��ұ� ������ �̰��� �����ٴ� �ǹ�
		// RedirectAttributes�� Model��ü�� ���� ���� ������.
		String midx = modelAndView.getModel().get("midx").toString();
		String memberName = modelAndView.getModel().get("memberName").toString();
		String memberId = modelAndView.getModel().get("memberId").toString();
		
		modelAndView.getModel().clear();  // �Ѿ���� �Ķ���� model���� ����� (������� �ʰ�) ��� �ּҿ� ������ �ʰ� �����°�
		
		HttpSession session = request.getSession();  // ���� �����ϱ�
		// ���ǿ� ��� ����
		if(midx != null) {  // midx�� ���� �ƴϸ� 
			// ���ǿ� ���� ��´�.
			session.setAttribute("midx", midx);
			session.setAttribute("memberId", memberId);
			session.setAttribute("memberName", memberName);
		}
	}


}




