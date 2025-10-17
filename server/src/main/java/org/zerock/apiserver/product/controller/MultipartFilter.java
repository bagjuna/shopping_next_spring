package org.zerock.apiserver.product.controller;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;



@Component
public class MultipartFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (request.getContentType() != null &&
                request.getContentType().startsWith("multipart/")) {
            System.out.println("Multipart Request received");
            System.out.println("Content-Length: " + request.getContentLengthLong());
        }
        chain.doFilter(request, response);
    }
}
