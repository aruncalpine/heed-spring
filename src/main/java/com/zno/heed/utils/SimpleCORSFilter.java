package com.zno.heed.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class SimpleCORSFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleCORSFilter.class);

	public SimpleCORSFilter() {
		LOG.info("SimpleCORSFilter init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, userToken, uri, X-Auth-Token");
		res.setHeader("Access-Control-Expose-Headers", "Content-Disposition,X-Suggested-Filename");
		HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals("OPTIONS")) {
            res.getWriter().print("OK");
            res.getWriter().flush();
        }else{
    		chain.doFilter(req, res);
        }
	}

	@Override
	public void init(FilterConfig filterConfig) { }

	@Override
	public void destroy() { }
}