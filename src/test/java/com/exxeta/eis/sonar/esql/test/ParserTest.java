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
package com.exxeta.eis.sonar.esql.test;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;


import org.junit.Test;

import com.exxeta.eis.sonar.esql.EsqlAstScanner;
import com.exxeta.eis.sonar.esql.EsqlConfiguration;
import com.exxeta.eis.sonar.esql.api.EsqlGrammar;
import com.google.common.base.Charsets;
import com.sonar.sslr.api.AstAndTokenVisitor;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.squid.AstScanner;
import com.sonar.sslr.squid.SquidAstVisitor;

public class ParserTest {

	private class Visitor extends SquidAstVisitor<EsqlGrammar> implements AstAndTokenVisitor {
		public boolean ok = false;

		public void visitToken(Token token) {
			ok = true;

		}
	}

	@Test
	public void test() {

		Visitor visitor = new Visitor();
		File dir = new File("src/test/resources");
		AstScanner<EsqlGrammar> scanner = EsqlAstScanner.create(new EsqlConfiguration(Charsets.UTF_8), visitor);
		parseDirectory(dir, scanner, visitor);

	}

	private void parseDirectory(File dir, AstScanner<EsqlGrammar> scanner, Visitor visitor) {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()){
				parseDirectory(f, scanner, visitor);
			}else if (f.getAbsolutePath().endsWith(".esql") && !f.getName().contains("parsingError")) {
				visitor.ok = false;
				scanner.scanFile(f);
				assertThat(visitor.ok).isTrue();
			}
		}
	}
}
