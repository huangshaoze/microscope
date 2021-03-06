package com.vipshop.microscope.query.user;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            recoreUserHistory(request);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    public void recoreUserHistory(ServletRequest request) {
        Assertion assertion = AssertionHolder.getAssertion();
        AttributePrincipal principal = assertion.getPrincipal();
        String infoSnapshot = principal.getName();
        String[] info = infoSnapshot.split("\\|");

        HashMap<String, String> user = new HashMap<String, String>();
        user.put("username", info[0]);
        user.put("history", ((HttpServletRequest) request).getRequestURL().toString());

//        StorageRepository.getStorageRepository().saveUser(user);

    }

    public User fetchLogin() {
        User user = new User();
        Assertion assertion = AssertionHolder.getAssertion();
        AttributePrincipal principal = assertion.getPrincipal();
        String infoSnapshot = principal.getName();
        String[] info = infoSnapshot.split("\\|");
        user.setName(info[0]);

        try {
            Map<String, Object> attributes = principal.getAttributes();
            user.setNumber(Long.valueOf((String) attributes.get("UserNum")));
            user.setChineseName(URLDecoder.decode((String) attributes.get("UserName"), "utf-8"));
            user.setDepartmentName(URLDecoder.decode((String) attributes.get("DeptName"), "utf-8"));
            user.setDepartmentStruction(URLDecoder.decode((String) attributes.get("DeptFullName"), "utf-8"));
            user.setEmail(URLDecoder.decode((String) attributes.get("UserEmail"), "utf-8"));
            user.setMobile((String) attributes.get("UserMobile"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean authenticate(User login) {
        return true;
    }


}
