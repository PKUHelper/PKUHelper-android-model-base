/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2017 Piasy, Luolc
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

import com.google.auto.value.AutoValue;

import android.support.annotation.Nullable;

/**
 * @author LuoLiangchen
 * @since 2017/1/5
 */
@AutoValue
public abstract class HttpClientConfig {

  public static Builder builder() {
    return new AutoValue_HttpClientConfig.Builder();
  }

  public abstract boolean enableLog();

  public abstract boolean enableCookie();

  @Nullable
  public abstract String clientName();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder enableLog(final boolean enableLog);

    public abstract Builder enableCookie(final boolean enableCookie);

    public abstract Builder clientName(@Nullable final String clientName);

    public abstract HttpClientConfig build();
  }
}
