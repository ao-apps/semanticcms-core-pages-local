/*
 * semanticcms-core-pages-local - Support for SemanticCMS pages produced by the local servlet container.
 * Copyright (C) 2017, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.encoding.Doctype;
import com.aoapps.encoding.Serialization;
import com.aoapps.encoding.servlet.DoctypeEE;
import com.aoapps.encoding.servlet.SerializationEE;
import com.aoapps.hodgepodge.util.Tuple2;
import com.aoapps.net.Path;
import com.aoapps.servlet.ServletContextCache;
import com.aoapps.servlet.ServletUtil;
import com.aoapps.servlet.http.Dispatcher;
import com.aoapps.servlet.http.HttpServletUtil;
import com.aoapps.servlet.http.NullHttpServletResponseWrapper;
import com.aoapps.servlet.subrequest.HttpServletSubRequestWrapper;
import com.aoapps.servlet.subrequest.HttpServletSubResponseWrapper;
import com.aoapps.servlet.subrequest.IHttpServletSubRequest;
import com.aoapps.servlet.subrequest.IHttpServletSubResponse;
import com.aoapps.tempfiles.servlet.TempFileContextEE;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.pages.CaptureLevel;
import com.semanticcms.core.pages.PageRepository;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.SkipPageException;

/**
 * Support for accessing pages from the local {@link ServletContext}.
 */
public abstract class LocalPageRepository implements PageRepository {


  // Matches AnyDocument.ENCODING
  public static final Charset ENCODING = StandardCharsets.UTF_8;

  // TODO: A way to register the current capture level, page, node, request, ...

  // TODO: Then auto resolve these before calling a subclass implementation of capturePage that takes additional parameters.

  protected final ServletContext servletContext;
  protected final ServletContextCache cache;
  protected final Path path;
  protected final String prefix;

  protected LocalPageRepository(ServletContext servletContext, Path path) {
    this.servletContext = servletContext;
    this.cache = ServletContextCache.getInstance(servletContext);
    this.path = path;
    String repositoryPathStr = path.toString();
    this.prefix = "/".equals(repositoryPathStr) ? "" : repositoryPathStr;
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
   * Must generate a toString based on the repository type and prefix.
   */
  @Override
  public abstract String toString();

  @Override
  public boolean isAvailable() {
    return true;
  }

  @Override
  public Page getPage(Path path, CaptureLevel level) throws IOException {
    Tuple2<String, RequestDispatcher> pathAndRequestDispatcher = getRequestDispatcher(path);
    if (pathAndRequestDispatcher == null) {
      return null;
    }
    final String requestDispatcherPath = pathAndRequestDispatcher.getElement1();
    final RequestDispatcher dispatcher = pathAndRequestDispatcher.getElement2();
    try {
      HttpServletRequest request = PageContext.getRequest();
      HttpServletResponse response = PageContext.getResponse();
      final IHttpServletSubRequest subRequest;
      if (request instanceof IHttpServletSubRequest) {
        subRequest = (IHttpServletSubRequest) request;
      } else {
        subRequest = new HttpServletSubRequestWrapper(request);
      }
      final IHttpServletSubResponse subResponse;
      if (response instanceof IHttpServletSubResponse) {
        subResponse = (IHttpServletSubResponse) response;
      } else {
        subResponse = new HttpServletSubResponseWrapper(response, TempFileContextEE.get(request));
      }
      // Clear request values that break captures
      CurrentNode.setCurrentNode(subRequest, null);
      CurrentPage.setCurrentPage(subRequest, null);
      // Set the content type
      Serialization currentSerialization = SerializationEE.getDefault(servletContext, subRequest);
      SerializationEE.set(subRequest, currentSerialization);
      ServletUtil.setContentType(subResponse, currentSerialization.getContentType(), /*AnyDocument.*/ENCODING);
      // Set the default doctype for all captures
      DoctypeEE.set(subRequest, Doctype.DEFAULT);
      // Set new capture context
      CurrentCaptureLevel.setCaptureLevel(subRequest, level);
      CaptureContext captureContext = new CaptureContext();
      CaptureContext.REQUEST_ATTRIBUTE.context(subRequest).set(captureContext);
      // Always capture as "GET" request
      subRequest.setMethod(HttpServletUtil.METHOD_GET);
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
            () -> Dispatcher.include(
                requestDispatcherPath,
                dispatcher,
                subRequest,
                // Discard all output
                new NullHttpServletResponseWrapper(subResponse)
            )
        );
      } catch (SkipPageException e) {
        // An individual page may throw SkipPageException which only terminates
        // the capture, not the request overall
      }
      Page capturedPage = captureContext.getCapturedPage();
      if (capturedPage == null) {
        throw new ServletException("No page captured, page=" + requestDispatcherPath);
      }
      return capturedPage;
    } catch (ServletException e) {
      throw new IOException(e);
    }
  }

  /**
   * Gets the path for the {@link RequestDispatcher} for the given path or {@code null}
   * if the page is known to not exist.
   */
  protected abstract Tuple2<String, RequestDispatcher> getRequestDispatcher(Path path) throws IOException;
}
