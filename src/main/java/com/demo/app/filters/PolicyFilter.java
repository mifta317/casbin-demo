package com.demo.app.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PolicyFilter extends OncePerRequestFilter {
  
  private static final List<String> EXCLUDE_URLS = Arrays.asList("/policies");

  private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
  
  @Autowired
  private Enforcer enforcer;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.info("Authorization -> {}", request.getHeader(HttpHeaders.AUTHORIZATION));
    log.info("method -> {}", request.getMethod());
    log.info("resource -> {}", request.getRequestURI());

    String subject = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isEmpty(subject)) {
      response.getOutputStream().write(new ObjectMapper().writeValueAsBytes("Authorization token is missing"));
      return;
    }
    
    boolean authorized = enforcer.enforce(subject, request.getRequestURI(), request.getMethod().toLowerCase());
    log.info("authorized -> {}", authorized);
    
    if (!authorized) {
      response.getOutputStream().write(new ObjectMapper().writeValueAsBytes("Access denied"));
      return;
    }

    doFilter(request, response, filterChain);

  }
  
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return EXCLUDE_URLS.stream().anyMatch(url -> PATH_MATCHER.match(url, request.getServletPath()));
  }

}
