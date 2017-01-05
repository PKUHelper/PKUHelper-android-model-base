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

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.compat.BuildConfig;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor for setting custom User-Agent header.
 *
 * @author LuoLiangchen
 * @since 2017/1/5
 */
public class UserAgentInterceptor implements Interceptor {

  private static final String HEADER_NAME = "User-Agent";

  private static String sOriginalUserAgentContent;

  private final String mClientName;

  public UserAgentInterceptor(final String clientName) {
    mClientName = clientName;
  }

  @Override
  public Response intercept(final Chain chain) throws IOException {
    final Request originalRequest = chain.request();
    final Request request = originalRequest.newBuilder()
        .removeHeader(HEADER_NAME)
        .addHeader(HEADER_NAME, generateUserAgent())
        .build();
    return chain.proceed(request);
  }

  @NonNull
  @VisibleForTesting
  String generateUserAgent() {
    final StringBuilder userAgent = new StringBuilder();
    userAgent.append(generateUserAgentPrefix());
    if (sOriginalUserAgentContent == null) {
      synchronized (UserAgentInterceptor.class) {
        if (sOriginalUserAgentContent == null) {
          final String httpAgent = getOriginalUserAgent();
          final Pattern pattern = Pattern.compile(".*(\\(.*\\)).*");
          final Matcher matcher = pattern.matcher(httpAgent);
          sOriginalUserAgentContent = matcher.matches() ? " " + matcher.group(1) : "";
        }
      }
    }
    userAgent.append(sOriginalUserAgentContent);
    return userAgent.toString();
  }

  @VisibleForTesting
  String generateUserAgentPrefix() {
    return mClientName + "/" + BuildConfig.VERSION_NAME;
  }

  @VisibleForTesting
  String getOriginalUserAgent() {
    return System.getProperty("http.agent");
  }
}
