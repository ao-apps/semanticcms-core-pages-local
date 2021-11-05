/*
 * semanticcms-core-pages-local - Support for SemanticCMS pages produced by the local servlet container.
 * Copyright (C) 2016, 2017, 2019, 2021  AO Industries, Inc.
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
 * along with semanticcms-core-pages-local.  If not, see <https://www.gnu.org/licenses/>.
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
public abstract class PageContextWriter {

	/** Make no instances. */
	private PageContextWriter() {throw new AssertionError();}

	public static PrintWriter write(int c) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.write(c);
		return out;
	}

	public static PrintWriter write(char[] cbuf) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.write(cbuf);
		return out;
	}

	public static PrintWriter write(char[] cbuf, int off, int len) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.write(cbuf, off, len);
		return out;
	}

	public static PrintWriter write(String str) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.write(str);
		return out;
	}

	public static PrintWriter write(String str, int off, int len) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.write(str, off, len);
		return out;
	}

	public static PrintWriter print(boolean b) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(b);
		return out;
	}

	public static PrintWriter print(char c) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(c);
		return out;
	}

	public static PrintWriter print(int i) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(i);
		return out;
	}

	public static PrintWriter print(long l) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(l);
		return out;
	}

	public static PrintWriter print(float f) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(f);
		return out;
	}

	public static PrintWriter print(double d) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(d);
		return out;
	}

	public static PrintWriter print(char[] s) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(s);
		return out;
	}

	public static PrintWriter print(String s) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(s);
		return out;
	}

	public static PrintWriter print(Object obj) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.print(obj);
		return out;
	}

	public static PrintWriter println() throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println();
		return out;
	}

	public static PrintWriter println(boolean x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
	}

	public static PrintWriter println(char x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
	}

	public static PrintWriter println(int x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
	}

	public static PrintWriter println(long x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
	}

	public static PrintWriter println(float x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
	}

	public static PrintWriter println(double x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
	}

	public static PrintWriter println(char[] x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
	}

	public static PrintWriter println(String x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
	}

	public static PrintWriter println(Object x) throws IOException {
		PrintWriter out = PageContext.getOut();
		out.println(x);
		return out;
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
}
