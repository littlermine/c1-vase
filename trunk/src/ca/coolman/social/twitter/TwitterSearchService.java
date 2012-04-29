/*
 * Copyright (c) 2012, Eric Coolman, 1815750 Ontario Inc. and/or its 
 * affiliates. All rights reserved.
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  1815750 Ontario Inc designates 
 * this  * particular file as subject to the "Classpath" exception as provided
 * in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact 1815750 Ontario Inc. through http://www.coolman.ca/ if you 
 * need additional information or have any questions.
 */
package ca.coolman.social.twitter;

import com.codename1.io.ConnectionRequest;

/**
 * @author ecoolman
 *
 */
public class TwitterSearchService extends ConnectionRequest {
	public final static String BASEURL = "http://search.twitter.com/search.";
	public final static String FORMAT_XML = "xml";
	public final static String FORMAT_JSON = "json";
	private final static String ARG_QUERY = "q";
	private final static String ARG_RESULTS_PER_PAGE = "rpp";
	private final static String ARG_PAGE = "page";
	
	public TwitterSearchService(String query) {
		this(query, FORMAT_JSON);
	}

	public TwitterSearchService(String query, String format) {
		setUrl(BASEURL + FORMAT_JSON);
		setPost(false);
		addArgument(ARG_QUERY, query);
	}

	public void setResultsPerPage(int rpp) {
		addArgument(ARG_RESULTS_PER_PAGE, Integer.toString(rpp));
	}
	
	public void setPage(int page) {
		addArgument(ARG_PAGE, Integer.toString(page));
	}
}
