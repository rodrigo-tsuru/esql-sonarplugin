/*
 * Sonar ESQL Plugin
 * Copyright (C) 2013 Thomas Pohl and EXXETA AG
 * http://www.exxeta.de
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.exxeta.eis.sonar.esql.core;


import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

import com.exxeta.eis.sonar.esql.EsqlPlugin;

public class Esql extends AbstractLanguage {

  public static final String KEY = "esql";

  private Settings settings;

  public Esql(Settings configuration) {
    super(KEY, "Esql");
    this.settings = configuration;
  }

  public Settings getSettings() {
    return this.settings;
  }

  public String[] getFileSuffixes() {
    String[] suffixes = settings.getStringArray(EsqlPlugin.FILE_SUFFIXES_KEY);
    if (suffixes == null || suffixes.length == 0) {
      suffixes = StringUtils.split(EsqlPlugin.FILE_SUFFIXES_DEFVALUE, ",");
    }
    return suffixes;
  }

}
