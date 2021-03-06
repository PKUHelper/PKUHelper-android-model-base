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

package com.pkuhelper.model.base.jsr310;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/16.
 *
 * Implementation of {@link ThreeTenAbpDelegate}.
 */
@Singleton
public class ThreeTenAbpDelegateImpl implements ThreeTenAbpDelegate {

  private final Application mApplication;

  /**
   * Create instance with the given {@link Application} object. Used to initialize the
   * ThreeTenABP library.
   *
   * @param application the given {@link Application} object. Used to initialize the ThreeTenABP
   * library.
   */
  @Inject
  ThreeTenAbpDelegateImpl(final Application application) {
    this.mApplication = application;
  }

  @Override
  public void init() {
    AndroidThreeTen.init(mApplication);
  }
}
