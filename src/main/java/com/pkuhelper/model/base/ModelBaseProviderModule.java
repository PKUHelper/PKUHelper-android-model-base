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

package com.pkuhelper.model.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.moczul.ok2curl.CurlInterceptor;
import com.pkuhelper.model.base.gson.GsonConfig;
import com.pkuhelper.model.base.httpclient.HttpClientConfig;
import com.pkuhelper.model.base.httpclient.UserAgentInterceptor;
import com.pkuhelper.model.base.jsr310.ZonedDateTimeJsonConverter;
import com.pkuhelper.model.base.retrofit.RetrofitConfig;

import org.threeten.bp.ZonedDateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * @author LuoLiangchen
 * @since 2017/1/4
 */
@Module
public class ModelBaseProviderModule {

  @Singleton
  @Provides
  Gson provideGson(final GsonConfig config) {
    final GsonBuilder builder = new GsonBuilder();
    if (config.autoGsonTypeAdapterFactory() != null) {
      builder.registerTypeAdapterFactory(config.autoGsonTypeAdapterFactory());
    }
    if (config.fieldNamingPolicy() != null) {
      builder.setFieldNamingPolicy(config.fieldNamingPolicy());
    }
    return builder
        .registerTypeAdapter(
            ZonedDateTime.class,
            new ZonedDateTimeJsonConverter(config.dateTimeFormatter()))
        .setDateFormat(config.dateFormatString())
        .setPrettyPrinting()
        .create();
  }

  @Singleton
  @Provides
  OkHttpClient provideHttpClient(final HttpClientConfig config) {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    if (config.enableLog()) {
      builder
          .addNetworkInterceptor(new StethoInterceptor())
          .addInterceptor(new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message))
              .setLevel(HttpLoggingInterceptor.Level.BODY))
          .addInterceptor(new CurlInterceptor(message -> Timber.tag("Ok2Curl").d(message)));
    }
    if (config.clientName() != null) {
      builder.addInterceptor(new UserAgentInterceptor(config.clientName()));
    }
    return builder.build();
  }

  @Singleton
  @Provides
  Retrofit provideRetrofit(final RetrofitConfig config, final OkHttpClient okHttpClient,
                           final Gson gson) {
    return new Retrofit.Builder().baseUrl(config.baseUrl())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build();
  }
}
