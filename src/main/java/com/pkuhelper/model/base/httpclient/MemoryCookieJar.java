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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * A {@link CookieJar} implementation which enables cookies in memory level.
 *
 * <p>Using a Map to store cookies which are classified by the host of {@code okhttp3.HttpUrl}</p>
 *
 * @author LuoLiangchen
 * @since 2017/1/6
 */
public class MemoryCookieJar implements CookieJar {

  private static final List<Cookie> EMPTY_COOKIES = new ArrayList<>();

  private final Map<String, List<Cookie>> mCookieStore = new ConcurrentHashMap<>();

  @Override
  public void saveFromResponse(final HttpUrl url, final List<Cookie> cookies) {
    mCookieStore.put(url.host(), cookies);
  }

  @Override
  public List<Cookie> loadForRequest(final HttpUrl url) {
    final List<Cookie> cookies = mCookieStore.get(url.host());
    return cookies == null ? EMPTY_COOKIES : cookies;
  }
}
