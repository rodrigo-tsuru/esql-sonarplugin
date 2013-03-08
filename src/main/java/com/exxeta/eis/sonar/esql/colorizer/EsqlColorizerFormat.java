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

package com.exxeta.eis.sonar.esql.colorizer;

import com.exxeta.eis.sonar.esql.api.EsqlKeyword;
import com.exxeta.eis.sonar.esql.core.Esql;
import com.exxeta.eis.sonar.esql.lexer.EsqlLexer;
import com.google.common.collect.ImmutableList;


import org.sonar.api.web.CodeColorizerFormat;
import org.sonar.colorizer.*;

import java.util.List;

public class EsqlColorizerFormat extends CodeColorizerFormat {

  public EsqlColorizerFormat() {
    super(Esql.KEY);
  }

  @Override
  public List<Tokenizer> getTokenizers() {
    return ImmutableList.of(
    	new RegexpTokenizer("<span class=\"s\">", "</span>", EsqlLexer.COMMENT),
        new StringTokenizer("<span class=\"s\">", "</span>"),
        new CDocTokenizer("<span class=\"cd\">", "</span>"),
        new JavadocTokenizer("<span class=\"cppd\">", "</span>"),
        new CppDocTokenizer("<span class=\"cppd\">", "</span>"),
        new CaseInsensitiveKeywordsTokenizer("<span class=\"k\">", "</span>", EsqlKeyword.keywordValues()));
  }

}
