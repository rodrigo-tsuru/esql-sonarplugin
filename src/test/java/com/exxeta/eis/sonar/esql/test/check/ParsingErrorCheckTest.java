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
package com.exxeta.eis.sonar.esql.test.check;

import static org.hamcrest.Matchers.containsString;

import java.io.File;


import org.junit.Test;
import org.sonar.squid.api.SourceFile;

import com.exxeta.eis.sonar.esql.EsqlAstScanner;
import com.exxeta.eis.sonar.esql.check.ParsingErrorCheck;
import com.sonar.sslr.squid.checks.CheckMessagesVerifier;

public class ParsingErrorCheckTest {

  @Test
  public void test() {
    SourceFile file = EsqlAstScanner.scanSingleFile(new File("src/test/resources/parsingError.esql"), new ParsingErrorCheck());
    CheckMessagesVerifier.verify(file.getCheckMessages())
        .next().atLine(6).withMessageThat(containsString("Parse error"))
        .noMore();
  }

}
