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
