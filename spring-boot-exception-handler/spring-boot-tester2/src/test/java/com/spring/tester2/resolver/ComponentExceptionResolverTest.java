package com.spring.tester2.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.naming.AuthenticationException;

@SpringBootTest
public class ComponentExceptionResolverTest {

    @Autowired
    private ExceptionResolverComponent component;

    @Test
    public void testExceptionResolver() {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException authException = new AuthenticationException("Auth Custom Exception");
        this.component.testResolveException(request, response, authException);
    }

    @Test
    public void testExceptionWithoutResolver() throws Exception {
        this.component.testThrowException();
    }
}
