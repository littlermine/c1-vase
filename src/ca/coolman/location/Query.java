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
package ca.coolman.location;

/**
 * A search query subject.
 * 
 * @author Eric Coolman
 *
 */
public class Query implements LookupSubject {
	private String query;
	
	public Query(String q) {
		this.query = q;
	}
	
	/**
	 * Construct a query from a given location.  Will first attempt to construct
	 * a query for [city], [region], [country].  If none of those tokens are
	 * present, a query for postal code is performed instead.
	 * 
	 * @param fromLocation
	 */
	public Query(LookupLocation fromLocation) {
		String q = "";
		if (fromLocation.getCityName() != null) {
			q += fromLocation.getCityName();
		}
		if (fromLocation.getRegionName() != null) {
			if (q.length() > 0) {
				q += ",";
			}
			q += fromLocation.getRegionName();
		}
		if (fromLocation.getCountryName() != null) {
			if (q.length() > 0) {
				q += ",";
			}
			q += fromLocation.getCountryName();
		}
		// if no address, use postalcode
		if (q.length() == 0) {
			q = fromLocation.getPostalCode();
		}
		this.query = q;
	}
	
	public String getQuery() {
		return query;
	}
}
