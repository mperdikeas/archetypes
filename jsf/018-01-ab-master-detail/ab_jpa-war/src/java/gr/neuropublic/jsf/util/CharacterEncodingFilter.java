package gr.neuropublic.jsf.util;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    @Override
        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        // resp.setCharacterEncoding("UTF-8"); // according to the following link:
        // http://stackoverflow.com/questions/9634230/typing-chinese-with-primefaces-peditor-component/9839362#9839362
        // it is redundant to set the character encoding for the responses.
        chain.doFilter(req, resp);
    }

    @Override
        public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
        public void destroy() {
    }
}