/*
 * Copyright 2014 NAVER Corp.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.navercorp.pinpoint.plugin.jdk.http;

import static com.navercorp.pinpoint.bootstrap.plugin.test.Expectations.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.navercorp.pinpoint.bootstrap.plugin.test.PluginTestVerifier;
import com.navercorp.pinpoint.bootstrap.plugin.test.PluginTestVerifierHolder;
import com.navercorp.pinpoint.test.plugin.JvmVersion;
import com.navercorp.pinpoint.test.plugin.PinpointPluginTestSuite;

/**
 * @author Jongho Moon
 *
 */
@RunWith(PinpointPluginTestSuite.class)
@JvmVersion({6, 7, 8})
public class HttpURLConnectionIT {

    @Test
    public void test() throws Exception {
        URL url = new URL("http://www.naver.com");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.getHeaderFields();
        
        PluginTestVerifier verifier = PluginTestVerifierHolder.getInstance();
        verifier.printCache();
        
        Class<?> targetClass = Class.forName("sun.net.www.protocol.http.HttpURLConnection");
        Method getInputStream = targetClass.getMethod("getInputStream");
        
        verifier.verifyTraceCount(1);
        verifier.verifyTrace(event("JDK_HTTPURLCONNECTOR", getInputStream, null, null, "www.naver.com", annotation("http.url", "http://www.naver.com")));
    }
    
    @Test
    public void testConnectTwice() throws Exception {
        URL url = new URL("http://www.naver.com");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        
        connection.connect();
        connection.getInputStream();
        
        PluginTestVerifier verifier = PluginTestVerifierHolder.getInstance();
        verifier.printCache();
        
        Class<?> targetClass = Class.forName("sun.net.www.protocol.http.HttpURLConnection");
        Method connect = targetClass.getMethod("connect");
        
        verifier.verifyTraceCount(1);
        verifier.verifyTrace(event("JDK_HTTPURLCONNECTOR", connect, null, null, "www.naver.com", annotation("http.url", "http://www.naver.com")));
    }
    
    @Test
    public void testConnecting() throws Exception {
        URL url = new URL("http://no.such.url");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        
        try {
            connection.connect();
        } catch (UnknownHostException e) {
            // ignore
        }
        
        try {
            connection.connect();
        } catch (UnknownHostException e) {
            // ignore
        }
        
        Field field = null;
        try {
            field = connection.getClass().getDeclaredField("connecting");
        } catch (NoSuchFieldException ignored) {
            
        }
        
         
        
        PluginTestVerifier verifier = PluginTestVerifierHolder.getInstance();
        verifier.printCache();
        
        Class<?> targetClass = Class.forName("sun.net.www.protocol.http.HttpURLConnection");
        Method getInputStream = targetClass.getMethod("connect");
        
        verifier.verifyTrace(event("JDK_HTTPURLCONNECTOR", getInputStream, null, null, "no.such.url", annotation("http.url", "http://no.such.url")));
        
        if (field == null) {
            // JDK 6, 7
            verifier.verifyTrace(event("JDK_HTTPURLCONNECTOR", getInputStream, null, null, "no.such.url", annotation("http.url", "http://no.such.url")));
        }
        
        verifier.verifyTraceCount(0);
    }
}
