package com.pkuhelper.model.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.pkuhelper.model.base.config.GsonConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author LuoLiangchen
 * @since 2017/1/4
 */

@Module
public class ProviderModule {

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
        .setDateFormat(config.dateFormatString())
        .setPrettyPrinting()
        .create();
  }
}
