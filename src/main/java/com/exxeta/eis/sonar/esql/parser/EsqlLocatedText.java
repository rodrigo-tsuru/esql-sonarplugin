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
import org.sonar.sslr.text.Text;
import org.sonar.sslr.text.TextCharSequence;
import org.sonar.sslr.text.TextLocation;

public class EsqlLocatedText extends PlainText {

	private LocatedText innerText;

	public EsqlLocatedText(LocatedText arg0) {
		super(arg0.toString().toUpperCase().toCharArray());
		innerText = arg0;
	}

	public TextCharSequence sequence() {
		TextCharSequence result = innerText.sequence();
		return result;
	}

	@Override
	public TextLocation getLocation(int index) {
		return innerText.getLocation(index);
	}
	
	

}
