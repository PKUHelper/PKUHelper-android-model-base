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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author LuoLiangchen
 * @since 2017/1/5
 */
public class UserAgentInterceptorTest {

  private UserAgentInterceptor mUserAgentInterceptor;

  @Before
  public void setup() {
    mUserAgentInterceptor = spy(new UserAgentInterceptor("pkuhelper"));
    when(mUserAgentInterceptor.generateUserAgentPrefix()).thenReturn("pkuhelper/3.0.0");
    when(mUserAgentInterceptor.getOriginalUserAgent()).thenReturn(
        "Mozilla/5.0 (Linux; U; Android 6.0.1; Nexus 6P Build/MTC20L) something else");
  }

  @Test
  public void generateUserAgent() {
    assertEquals("pkuhelper/3.0.0 (Linux; U; Android 6.0.1; Nexus 6P Build/MTC20L)",
        mUserAgentInterceptor.generateUserAgent());
  }

  @Test
  public void interceptWithNewUserAgent() throws IOException {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(mUserAgentInterceptor)
        .build();
    Request originRequest = new Request.Builder()
        .url("https://api.github.com")
        .header("User-Agent", "something else")
        .build();
    Response response = okHttpClient.newCall(originRequest).execute();
    Request request = response.request();
    assertEquals(mUserAgentInterceptor.generateUserAgent(), request.header("User-Agent"));
  }
}
