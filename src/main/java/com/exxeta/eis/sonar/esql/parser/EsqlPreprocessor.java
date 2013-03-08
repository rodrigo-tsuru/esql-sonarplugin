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

import org.sonar.sslr.internal.text.LocatedText;
import org.sonar.sslr.internal.text.PlainText;
import org.sonar.sslr.text.Preprocessor;
import org.sonar.sslr.text.PreprocessorContext;
import org.sonar.sslr.text.Text;

public class EsqlPreprocessor implements Preprocessor {

	public Text process(PreprocessorContext arg0) {
		Text result;
		if (arg0.getInput() instanceof LocatedText) {
			result = new EsqlLocatedText((LocatedText)arg0.getInput());
		} else {
			result = new PlainText(arg0.getInput().sequence().toString()
					.toUpperCase().toCharArray());
		}
		return result;
	}

}
