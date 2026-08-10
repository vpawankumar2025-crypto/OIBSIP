package com.library.config;

import com.library.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Simple session-based guard: protects /admin/** (admin only) and
 * /user/** (any logged-in user) routes. Redirects to /login if the
 * session has no authenticated user, or to / if a non-admin tries
 * to reach an admin route.
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        User currentUser = session == null ? null : (User) session.getAttribute("currentUser");
        String path = request.getRequestURI();

        if (currentUser == null) {
            response.sendRedirect("/login");
            return false;
        }
        if (path.startsWith("/admin") && !currentUser.isAdmin()) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
