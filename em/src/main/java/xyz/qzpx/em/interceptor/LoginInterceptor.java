package xyz.qzpx.em.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //HttpSession session = request.getSession();
        //String contextPath = session.getServletContext().getContextPath();
        //String uri = request.getRequestURI();
        //uri = StringUtils.remove(uri, contextPath + "/");
        //
        //String[] requireAuthPages = new String[]{"index"};
        //
        //if (beginWith(uri, requireAuthPages)) {
        //    User user = (User) session.getAttribute("user");
        //    if (user == null) {
        //        response.sendRedirect("login");
        //        return false;
        //    }
        //}
        //return true;

        // 放行 options 请求，否则无法让前端带上自定义的 header 信息，导致 sessionID 改变，shiro 验证失败
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        Subject subject = SecurityUtils.getSubject();
        System.out.println("isRemembered: " + subject.isRemembered());
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return false;
        }
        return true;
    }

    private boolean beginWith(String uri, String[] requireAuthPages) {
        boolean result = false;
        for (String page : requireAuthPages) {
            if (StringUtils.startsWith(uri, page)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
