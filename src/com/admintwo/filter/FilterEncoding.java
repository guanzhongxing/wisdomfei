package com.admintwo.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterEncoding implements Filter {
	protected String encoding; // 接收字符编码
	protected FilterConfig filterConfig; // 初始化配置

	public void init(FilterConfig filterConfig) throws ServletException {
		// 从web.xml文件中读取encoding的值
		encoding = filterConfig.getInitParameter("encoding");
	}

	// doFilter方法
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	public void destroy() {

	}
}
