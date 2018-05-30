/*
 * semanticcms-core-pages-local - Support for SemanticCMS pages produced by the local servlet container.
 * Copyright (C) 2017, 2018  AO Industries, Inc.
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

import com.aoindustries.lang.NotImplementedException;
import com.aoindustries.net.Path;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.pages.CaptureLevel;
import com.semanticcms.core.pages.PageRepository;
import java.io.IOException;
import javax.servlet.ServletContext;

/**
 * Support for accessing pages from the local {@link ServletContext}.
 */
abstract public class LocalPageRepository implements PageRepository {


	// TODO: A way to register the current capture level, page, node, request, ...

	// TODO: Then auto resolve these before calling a subclass implementation of getPage that takes additional parameters.

	@Override
	public Page getPage(Path path, CaptureLevel level) throws IOException {
		throw new NotImplementedException();
	}
}
