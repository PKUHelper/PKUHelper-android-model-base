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

package com.pkuhelper.model.base.config;

import com.google.auto.value.AutoValue;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.TypeAdapterFactory;

import android.support.annotation.Nullable;

import org.threeten.bp.format.DateTimeFormatter;

/**
 * @author LuoLiangchen
 * @since 2017/1/4
 */

@AutoValue
public abstract class GsonConfig {

  public static Builder builder() {
    return new AutoValue_GsonConfig.Builder();
  }

  public abstract DateTimeFormatter dateTimeFormatter();

  public abstract String dateFormatString();

  @Nullable
  public abstract TypeAdapterFactory autoGsonTypeAdapterFactory();

  @Nullable
  public abstract FieldNamingPolicy fieldNamingPolicy();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder dateTimeFormatter(final DateTimeFormatter dateTimeFormatter);

    public abstract Builder dateFormatString(final String dateFormatString);

    public abstract Builder autoGsonTypeAdapterFactory(
        @Nullable final TypeAdapterFactory typeAdapterFactory);

    public abstract Builder fieldNamingPolicy(@Nullable FieldNamingPolicy fieldNamingPolicy);

    public abstract GsonConfig build();
  }
}
