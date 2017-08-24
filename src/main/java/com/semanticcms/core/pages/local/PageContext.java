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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.SkipPageException;

/**
 * <p>
 * HttpServletRequest, HttpServletResponse, and the response PrintWriter are frequently accessed
 * while also frequently updated by elements for capture.  It is tedious to pass these objects around
 * all over.  Also, with the lambda scoping in Java 8, it is easy to use the wrong object from a
 * different scope (and possibly capturing context).
 * </p>
 * <p>
 * Each context is intended to be established and used within a single servlet implementation.
 * Do not assume correct management of PageContext between different pages, servlets, or files.
 * If in doubt, skip this shortcut and pass explicit object references around.
 * </p>
 *
 * @see  PageContextWriter
 */
final public class PageContext {

	static final ThreadLocal<ServletContext> servletContext = new ThreadLocal<ServletContext>();

	static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

	static final ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

	static final ThreadLocal<PrintWriter> out = new ThreadLocal<PrintWriter>();

	public static interface PageContextCallable {
		void call() throws ServletException, IOException, SkipPageException;
	}

	public static void newPageContext(ServletContext newServletContext, HttpServletRequest newRequest, HttpServletResponse newResponse, PageContextCallable target) throws ServletException, IOException, SkipPageException {
		final ServletContext oldServletContext = servletContext.get();
		final HttpServletRequest oldRequest = request.get();
		final HttpServletResponse oldResponse = response.get();
		final PrintWriter oldOut = out.get();
		PrintWriter newOut = null;
		try {
			if(newServletContext != oldServletContext) servletContext.set(newServletContext);
			if(newRequest != oldRequest) request.set(newRequest);
			if(newResponse != oldResponse) {
				response.set(newResponse);
				out.set(null);
			} else {
				newOut = oldOut;
			}
			target.call();
		} finally {
			if(newServletContext != oldServletContext) servletContext.set(oldServletContext);
			if(newRequest != oldRequest) request.set(oldRequest);
			if(newResponse != oldResponse) response.set(oldResponse);
			if(newOut != oldOut) out.set(oldOut);
		}
	}

	public static interface PageContextCallableSkip {
		void call() throws ServletException, IOException, SkipPageException;
	}

	/**
	 * Establishes a new page context.
	 * This usually does not need to be done directly as creating a page will
	 * establish the starting page context.
	 * 
	 * @see  Page#invoke(com.semanticcms.core.servlet.Page.Body)
	 */
	public static void newPageContextSkip(
		ServletContext newServletContext,
		HttpServletRequest newRequest,
		HttpServletResponse newResponse,
		PageContextCallableSkip target
	) throws ServletException, IOException, SkipPageException {
		final ServletContext oldServletContext = servletContext.get();
		final HttpServletRequest oldRequest = request.get();
		final HttpServletResponse oldResponse = response.get();
		final PrintWriter oldOut = out.get();
		PrintWriter newOut = null;
		try {
			if(newServletContext != oldServletContext) servletContext.set(newServletContext);
			if(newRequest != oldRequest) request.set(newRequest);
			if(newResponse != oldResponse) {
				response.set(newResponse);
				out.set(null);
			} else {
				newOut = oldOut;
			}
			target.call();
		} finally {
			if(newServletContext != oldServletContext) servletContext.set(oldServletContext);
			if(newRequest != oldRequest) request.set(oldRequest);
			if(newResponse != oldResponse) response.set(oldResponse);
			if(newOut != oldOut) out.set(oldOut);
		}
	}

	public static interface PageContextCallableSkipE<E extends Throwable> {
		void call() throws E, ServletException, IOException, SkipPageException;
	}

	public static <E extends Throwable> void newPageContextSkipE(ServletContext newServletContext, HttpServletRequest newRequest, HttpServletResponse newResponse, PageContextCallableSkipE<E> target) throws E, ServletException, IOException, SkipPageException {
		final ServletContext oldServletContext = servletContext.get();
		final HttpServletRequest oldRequest = request.get();
		final HttpServletResponse oldResponse = response.get();
		final PrintWriter oldOut = out.get();
		PrintWriter newOut = null;
		try {
			if(newServletContext != oldServletContext) servletContext.set(newServletContext);
			if(newRequest != oldRequest) request.set(newRequest);
			if(newResponse != oldResponse) {
				response.set(newResponse);
				out.set(null);
			} else {
				newOut = oldOut;
			}
			target.call();
		} finally {
			if(newServletContext != oldServletContext) servletContext.set(oldServletContext);
			if(newRequest != oldRequest) request.set(oldRequest);
			if(newResponse != oldResponse) response.set(oldResponse);
			if(newOut != oldOut) out.set(oldOut);
		}
	}

	public static interface PageContextCallableSkipEE<E1 extends Throwable, E2 extends Throwable> {
		void call() throws E1, E2, ServletException, IOException, SkipPageException;
	}

	public static <E1 extends Throwable, E2 extends Throwable> void newPageContextSkipEE(ServletContext newServletContext, HttpServletRequest newRequest, HttpServletResponse newResponse, PageContextCallableSkipEE<E1,E2> target) throws E1, E2, ServletException, IOException, SkipPageException {
		final ServletContext oldServletContext = servletContext.get();
		final HttpServletRequest oldRequest = request.get();
		final HttpServletResponse oldResponse = response.get();
		final PrintWriter oldOut = out.get();
		PrintWriter newOut = null;
		try {
			if(newServletContext != oldServletContext) servletContext.set(newServletContext);
			if(newRequest != oldRequest) request.set(newRequest);
			if(newResponse != oldResponse) {
				response.set(newResponse);
				out.set(null);
			} else {
				newOut = oldOut;
			}
			target.call();
		} finally {
			if(newServletContext != oldServletContext) servletContext.set(oldServletContext);
			if(newRequest != oldRequest) request.set(oldRequest);
			if(newResponse != oldResponse) response.set(oldResponse);
			if(newOut != oldOut) out.set(oldOut);
		}
	}

	/**
	 * Gets the current servlet context.
	 *
	 * @throws  IllegalStateException if no context set
	 */
	public static ServletContext getServletContext() throws IllegalStateException {
		ServletContext s = servletContext.get();
		if(s == null) throw new IllegalStateException("No page context");
		return s;
	}

	/**
	 * Gets the current request.
	 *
	 * @throws  IllegalStateException if no context set
	 */
	public static HttpServletRequest getRequest() throws IllegalStateException {
		HttpServletRequest r = request.get();
		if(r == null) throw new IllegalStateException("No page context");
		return r;
	}

	/**
	 * Gets the current response.
	 *
	 * @throws  IllegalStateException if no context set
	 */
	public static HttpServletResponse getResponse() throws IllegalStateException {
		HttpServletResponse r = response.get();
		if(r == null) throw new IllegalStateException("No page context");
		return r;
	}

	/**
	 * Gets the current response writer.
	 *
	 * @throws  IllegalStateException if no context set
	 */
	public static PrintWriter getOut() throws IllegalStateException, IOException {
		PrintWriter o = out.get();
		if(o == null) {
			o = getResponse().getWriter();
			out.set(o);
		}
		return o;
	}

	/**
	 * Make no instances, all is done through thread locals.
	 */
	private PageContext() {
	}
}
