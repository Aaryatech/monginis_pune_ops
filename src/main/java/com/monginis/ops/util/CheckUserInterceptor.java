package com.monginis.ops.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CheckUserInterceptor extends HandlerInterceptorAdapter {

   
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws IOException, LoginFailException {

    	System.out.println("Intercept handler.."); 
    	
    	HttpSession session = request.getSession();

         String path = request.getRequestURI().substring(request.getContextPath().length());
         System.out.println("path is: "+path);
       
 		/*if(path.startsWith("/pdf")) {
 			return true;
 		}*/
         if(request.getServletPath().equals("/") || request.getServletPath().equals("/loginProcess") ||request.getServletPath().equals("/logout") ||request.getServletPath().equals("/login")){ //||request.getServletPath().equals("/logout")
        	 System.out.println("Login request");
             return true;
         }
         else 
         if(session == null || session.getAttribute("frDetails") == null || session.getAttribute("getFrDetails").equals("")) {
             request.setAttribute("emassage", "login failed");                
             response.sendRedirect(request.getContextPath()+"/sessionTimeOut");     
             return false;

         }else{                
             return true;
         }    

}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("post intercept hanlder");
		super.postHandle(request, response, handler, modelAndView);
	}
    
    
}