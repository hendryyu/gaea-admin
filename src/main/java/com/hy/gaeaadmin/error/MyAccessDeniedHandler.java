/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.error;

import org.springframework.stereotype.Component;


// handle 403 page
@Component
public class MyAccessDeniedHandler {}
//        implements AccessDeniedHandler {
//
//    private static Logger logger = LoggerFactory.getLogger(MyAccessDeniedHandler.class);
//
//    @Override
//    public void handle(HttpServletRequest httpServletRequest,
//                       HttpServletResponse httpServletResponse,
//                       AccessDeniedException e) throws IOException, ServletException {
//
//        Authentication auth
//                = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null) {
//            logger.info("User '" + auth.getName()
//                    + "' attempted to access the protected URL: "
//                    + httpServletRequest.getRequestURI());
//        }
//
//        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");
//
//    }
//}
