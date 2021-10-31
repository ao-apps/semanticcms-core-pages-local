/*
 * semanticcms-core-pages-local - Support for SemanticCMS pages produced by the local servlet container.
 * Copyright (C) 2013, 2014, 2015, 2016, 2017, 2020, 2021  AO Industries, Inc.
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

import com.aoapps.servlet.attribute.ScopeEE;
import com.semanticcms.core.model.Node;
import javax.servlet.ServletRequest;

/**
 * Tracking of the current node during request processing and capturing.
 */
public final class CurrentNode {

	/**
	 * Cleared and restored on request in CapturePage
	 */
	public static final ScopeEE.Request.Attribute<Node> REQUEST_ATTRIBUTE = ScopeEE.REQUEST.attribute("currentNode");

	public static Node getCurrentNode(ServletRequest request) {
		return REQUEST_ATTRIBUTE.context(request).get();
	}

	public static void setCurrentNode(ServletRequest request, Node node) {
		REQUEST_ATTRIBUTE.context(request).set(node);
	}

	/**
	 * Make no instances.
	 */
	private CurrentNode() {
	}
}
