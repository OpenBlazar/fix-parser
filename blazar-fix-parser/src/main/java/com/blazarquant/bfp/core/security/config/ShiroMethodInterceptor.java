/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.core.security.config;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.shiro.authz.aop.RoleAnnotationMethodInterceptor;

import java.lang.reflect.Method;

/**
 * @author Wojciech Zankowski
 */
public class ShiroMethodInterceptor implements MethodInterceptor {

    private RoleAnnotationMethodInterceptor roleAnnotationMethodInterceptor = new RoleAnnotationMethodInterceptor();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return roleAnnotationMethodInterceptor.invoke(new ShiroMethodInvocation(methodInvocation));
    }

    private static class ShiroMethodInvocation implements org.apache.shiro.aop.MethodInvocation {

        private MethodInvocation methodInvocation;

        public ShiroMethodInvocation(MethodInvocation methodInvocation) {
            this.methodInvocation = methodInvocation;
        }

        @Override
        public Object proceed() throws Throwable {
            return methodInvocation.proceed();
        }

        @Override
        public Method getMethod() {
            return methodInvocation.getMethod();
        }

        @Override
        public Object[] getArguments() {
            return methodInvocation.getArguments();
        }

        @Override
        public Object getThis() {
            return methodInvocation.getThis();
        }
    }
}
