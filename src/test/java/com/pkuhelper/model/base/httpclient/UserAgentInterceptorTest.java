/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2017 Luolc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.pkuhelper.model.base.httpclient;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author LuoLiangchen
 * @since 2017/1/5
 */
public class UserAgentInterceptorTest {

  private UserAgentInterceptor mUserAgentInterceptor;

  @Before
  public void setUp() {
    mUserAgentInterceptor = spy(new UserAgentInterceptor("pkuhelper"));
    when(mUserAgentInterceptor.generateUserAgentPrefix()).thenReturn("pkuhelper/3.0.0");
    when(mUserAgentInterceptor.getOriginalUserAgent()).thenReturn(
        "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) "
        + "AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
  }

  @Test
  public void generateUserAgentContent() {
    assertEquals("pkuhelper/3.0.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91)",
        mUserAgentInterceptor.generateUserAgent());
  }

  @Test
  public void interceptWithNewUserAgent() throws IOException, InterruptedException {
    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse());
    server.start();
    HttpUrl baseUrl = server.url("test");

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(mUserAgentInterceptor)
        .build();
    Request originRequest = new Request.Builder()
        .url(baseUrl.url())
        .header("User-Agent", "something else")
        .build();
    okHttpClient.newCall(originRequest).execute();
    RecordedRequest request = server.takeRequest();
    assertEquals(mUserAgentInterceptor.generateUserAgent(), request.getHeader("User-Agent"));

    server.shutdown();
  }
}
