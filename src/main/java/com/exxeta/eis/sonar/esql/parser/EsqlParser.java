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
package com.exxeta.eis.sonar.esql.parser;

import java.util.ArrayList;


import org.sonar.sslr.parser.ParserAdapter;
import org.sonar.sslr.text.PreprocessorsChain;

import com.exxeta.eis.sonar.esql.EsqlConfiguration;
import com.exxeta.eis.sonar.esql.api.EsqlGrammar;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.events.ParsingEventListener;


public class EsqlParser {
	  private EsqlParser() {
	  }
	public static Parser<EsqlGrammar> create(EsqlConfiguration conf, ParsingEventListener... parsingEventListeners) {
		ArrayList<org.sonar.sslr.text.Preprocessor> list  = new ArrayList<org.sonar.sslr.text.Preprocessor>();
		list.add(new EsqlPreprocessor());
		return new ParserAdapter<EsqlGrammar>(conf.getCharset(), new EsqlGrammarImpl(),new PreprocessorsChain(list));	
		}

}
