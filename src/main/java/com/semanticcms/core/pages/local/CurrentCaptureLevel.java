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
import com.semanticcms.core.pages.CaptureLevel;
import javax.servlet.ServletRequest;

/**
 * The current capture mode.
 */
public class CurrentCaptureLevel {

	private static final ScopeEE.Request.Attribute<CaptureLevel> CAPTURE_LEVEL_REQUEST_ATTRIBUTE =
		ScopeEE.REQUEST.attribute(CurrentCaptureLevel.class.getName() + ".captureLevel");

	/**
	 * Gets the capture level or {@link CaptureLevel#BODY} if none occurring.
	 */
	public static CaptureLevel getCaptureLevel(ServletRequest request) {
		return CAPTURE_LEVEL_REQUEST_ATTRIBUTE.context(request).getOrDefault(CaptureLevel.BODY);
	}

	public static void setCaptureLevel(ServletRequest request, CaptureLevel level) {
		CAPTURE_LEVEL_REQUEST_ATTRIBUTE.context(request).set(level);
	}

	/** Make no instances */
	private CurrentCaptureLevel() {}
}
