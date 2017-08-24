/*
 * semanticcms-core-pages-local - Support for SemanticCMS pages produced by the local servlet container.
 * Copyright (C) 2016, 2017  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of semanticcms-core-pages-local.
 *
 * semanticcms-core-pages-local is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * semanticcms-core-pages-local is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with semanticcms-core-pages-local.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.semanticcms.core.pages.local;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Also provides convenience access to all the PrintWriter methods, available through static import.
 *
 * @see  PageContext
 * @see  PrintWriter
 */
final public class PageContextWriter {

	public static void write(int c) throws IOException {
		PageContext.getOut().write(c);
	}

	public static void write(char cbuf[]) throws IOException {
		PageContext.getOut().write(cbuf);
	}

	public static void write(char cbuf[], int off, int len) throws IOException {
		PageContext.getOut().write(cbuf, off, len);
	}

	public static void write(String str) throws IOException {
		PageContext.getOut().write(str);
	}

	public static void write(String str, int off, int len) throws IOException {
		PageContext.getOut().write(str, off, len);
	}

	public static void print(boolean b) throws IOException {
		PageContext.getOut().print(b);
	}

	public static void print(char c) throws IOException {
		PageContext.getOut().print(c);
	}

	public static void print(int i) throws IOException {
		PageContext.getOut().print(i);
	}

	public static void print(long l) throws IOException {
		PageContext.getOut().print(l);
	}

	public static void print(float f) throws IOException {
		PageContext.getOut().print(f);
	}

	public static void print(double d) throws IOException {
		PageContext.getOut().print(d);
	}

	public static void print(char s[]) throws IOException {
		PageContext.getOut().print(s);
	}

	public static void print(String s) throws IOException {
		PageContext.getOut().print(s);
	}

	public static void print(Object obj) throws IOException {
		PageContext.getOut().print(obj);
	}

	public static void println() throws IOException {
		PageContext.getOut().println();
	}

	public static void println(boolean x) throws IOException {
		PageContext.getOut().println(x);
	}

	public static void println(char x) throws IOException {
		PageContext.getOut().println(x);
	}

	public static void println(int x) throws IOException {
		PageContext.getOut().println(x);
	}

	public static void println(long x) throws IOException {
		PageContext.getOut().println(x);
	}

	public static void println(float x) throws IOException {
		PageContext.getOut().println(x);
	}

	public static void println(double x) throws IOException {
		PageContext.getOut().println(x);
	}

	public static void println(char x[]) throws IOException {
		PageContext.getOut().println(x);
	}

	public static void println(String x) throws IOException {
		PageContext.getOut().println(x);
	}

	public static void println(Object x) throws IOException {
		PageContext.getOut().println(x);
	}

	public static PrintWriter printf(String format, Object ... args) throws IOException {
		return PageContext.getOut().printf(format, args);
	}

	public static PrintWriter printf(Locale l, String format, Object ... args) throws IOException {
		return PageContext.getOut().printf(l, format, args);
	}

	public static PrintWriter format(String format, Object ... args) throws IOException {
		return PageContext.getOut().format(format, args);
	}

	public static PrintWriter format(Locale l, String format, Object ... args) throws IOException {
		return PageContext.getOut().format(l, format, args);
	}

	public static PrintWriter append(CharSequence csq) throws IOException {
		return PageContext.getOut().append(csq);
	}

	public static PrintWriter append(CharSequence csq, int start, int end) throws IOException {
		return PageContext.getOut().append(csq, start, end);
	}

	public static PrintWriter append(char c) throws IOException {
		return PageContext.getOut().append(c);
	}

	/**
	 * Make no instances.
	 */
	private PageContextWriter() {
	}
}
