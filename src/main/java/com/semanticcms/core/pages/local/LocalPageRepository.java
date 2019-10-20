/*
 * semanticcms-core-pages-local - Support for SemanticCMS pages produced by the local servlet container.
 * Copyright (C) 2017, 2018, 2019  AO Industries, Inc.
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

import com.aoindustries.encoding.MediaType;
import com.aoindustries.net.Path;
import com.aoindustries.servlet.ServletContextCache;
import com.aoindustries.servlet.http.Dispatcher;
import com.aoindustries.servlet.http.Html;
import com.aoindustries.servlet.http.HttpServletUtil;
import com.aoindustries.servlet.http.NullHttpServletResponseWrapper;
import com.aoindustries.util.Tuple2;
import com.semanticcms.core.model.Node;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.pages.CaptureLevel;
import com.semanticcms.core.pages.PageRepository;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.SkipPageException;

/**
 * Support for accessing pages from the local {@link ServletContext}.
 */
abstract public class LocalPageRepository implements PageRepository {


	// TODO: A way to register the current capture level, page, node, request, ...

	// TODO: Then auto resolve these before calling a subclass implementation of capturePage that takes additional parameters.

	final protected ServletContext servletContext;
	final protected ServletContextCache cache;
	final protected Path path;
	final protected String prefix;

	protected LocalPageRepository(ServletContext servletContext, Path path) {
		this.servletContext = servletContext;
		this.cache = ServletContextCache.getCache(servletContext);
		this.path = path;
		String repositoryPathStr = path.toString();
		this.prefix = repositoryPathStr.equals("/") ? "" : repositoryPathStr;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * Gets the path, without any trailing slash except for "/".
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Gets the prefix useful for direct path concatenation, which is the path itself except empty string for "/".
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Must generate a toString based on the repository type and prefix
	 */
	@Override
	abstract public String toString();

	@Override
	public boolean isAvailable() {
		return true;
	}

	@Override
	public Page getPage(Path path, CaptureLevel level) throws IOException {
		Tuple2<String, RequestDispatcher> pathAndRequestDispatcher = getRequestDispatcher(path);
		if(pathAndRequestDispatcher == null) return null;
		final String requestDispatcherPath = pathAndRequestDispatcher.getElement1();
		final RequestDispatcher dispatcher = pathAndRequestDispatcher.getElement2();
		try {
			final HttpServletRequest request = PageContext.getRequest();
			final HttpServletResponse response = PageContext.getResponse();
			// Perform new capture
			Node oldNode = CurrentNode.getCurrentNode(request);
			Page oldPage = CurrentPage.getCurrentPage(request);
			try {
				// Clear request values that break captures
				if(oldNode != null) CurrentNode.setCurrentNode(request, null);
				if(oldPage != null) CurrentPage.setCurrentPage(request, null);
				CaptureLevel oldCaptureLevel = CurrentCaptureLevel.getCaptureLevel(request);
				CaptureContext oldCaptureContext = CaptureContext.getCaptureContext(request);
				try {
					// Set the doctype to html5 for all captures
					Object oldDocType = request.getAttribute(Html.DocType.class.getName());
					try {
						Html.DocType.set(request, Html.DocType.html5);
							// Set the response content type to "application/xhtml+xml" for a consistent starting point for captures
							String oldContentType = response.getContentType();
							try {
								response.setContentType(MediaType.XHTML.getContentType());
								// Set new capture context
								CurrentCaptureLevel.setCaptureLevel(request, level);
								CaptureContext captureContext = new CaptureContext();
								request.setAttribute(CaptureContext.CAPTURE_CONTEXT_REQUEST_ATTRIBUTE_NAME, captureContext);
								// TODO: Set more "current" for request and response
								// TODO: Is PageContext useful for this?
								// TODO: capturedPage = repository.capturePage(pageRef.getPath(), level);
								// Include the page resource, discarding any direct output
								try {
									// Clear PageContext on include
									PageContext.newPageContextSkip(
										null,
										null,
										null,
										new PageContext.PageContextRunnableSkip() {
											@Override
											public void run() throws ServletException, IOException, SkipPageException {
												Dispatcher.include(
													requestDispatcherPath,
													dispatcher,
													// Always capture as "GET" request
													HttpServletUtil.METHOD_GET.equals(request.getMethod())
														// Is already "GET"
														? request
														// Wrap to make "GET"
														: new HttpServletRequestWrapper(request) {
															@Override
															public String getMethod() {
																return HttpServletUtil.METHOD_GET;
															}
														},
													new NullHttpServletResponseWrapper(response)
												);
											}
										}
									);
								} catch(SkipPageException e) {
									// An individual page may throw SkipPageException which only terminates
									// the capture, not the request overall
								}
								Page capturedPage = captureContext.getCapturedPage();
								if(capturedPage == null) throw new ServletException("No page captured, page=" + requestDispatcherPath);
								return capturedPage;
							} finally {
								if(oldContentType != null) response.setContentType(oldContentType);
							}
					} finally {
						request.setAttribute(Html.DocType.class.getName(), oldDocType);
					}
				} finally {
					// Restore previous capture context
					CurrentCaptureLevel.setCaptureLevel(request, oldCaptureLevel);
					request.setAttribute(CaptureContext.CAPTURE_CONTEXT_REQUEST_ATTRIBUTE_NAME, oldCaptureContext);
				}
			} finally {
				if(oldNode != null) CurrentNode.setCurrentNode(request, oldNode);
				if(oldPage != null) CurrentPage.setCurrentPage(request, oldPage);
			}
		} catch(ServletException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Gets the path for the {@link RequestDispatcher} for the given path or {@code null}
	 * if the page is known to not exist.
	 */
	abstract protected Tuple2<String,RequestDispatcher> getRequestDispatcher(Path path) throws IOException;
}
