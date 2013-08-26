/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.filter;

import com.onsemi.onquiry.entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author iamejay
 */
public class LoginFilter implements Filter {

    private List<String> allowedUrls;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(allowedUrls == null) {
            allowedUrls = new ArrayList<String>();
            allowedUrls.add(filterConfig.getInitParameter("loginURI"));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        
        User user = (User) session.getAttribute("user");
        String requestURI = httpServletRequest.getRequestURI();
        
        if(user == null && !allowedUrls.contains(requestURI) && !requestURI.contains("javax.faces.resource")) {
            RequestDispatcher reqDispatcher = httpServletRequest.getRequestDispatcher("login.xhtml");
            reqDispatcher.forward(request, response);
            return;
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
}
