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

import java.io.IOException;
import java.io.InputStream;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;

/**
 * A reverse geocoder implementation using OpenStreetmaps provider.
 * 
 * <pre>
 * See http://wiki.openstreetmap.org/wiki/Nominatim#Reverse_Geocoding_.2F_Address_lookup
 * </pre>
 * 
 * @author ecoolman
 * 
 */
public class OpenStreetmapReverseGeocoderProvider implements LookupProvider {
	private final static String BASEURL = "http://nominatim.openstreetmap.org/";
	private final static String REVERSE_GEOCODE = "reverse";
	private final static String QUERY = "search";
	private final static String ARG_FORMAT = "format";
	private final static String ARG_QUERY = "q";
	private final static String ARG_LATITUDE = "lat";
	private final static String ARG_LONGITUDE = "lon";
	private final static String ARG_ZOOM = "zoom";
	private final static String ARG_ADDRESSDETAILS = "addressdetails";
	private final static String FORMAT_JSON = "json";
	// 0=country, 18=house/building
	private final static int DEFAULT_ZOOM = 18;
	// include a breakdown of the address into elements
	private final static int DEFAULT_ADDRESSDETAILS = 1;

	class ServiceRequest extends ConnectionRequest {
		private LookupLocation location;

		public ServiceRequest(Coordinate coord) {
			setUrl(BASEURL + REVERSE_GEOCODE + '?' + ARG_FORMAT + '=' + FORMAT_JSON + '&' + ARG_LATITUDE + '='
					+ coord.getLatitude() + '&' + ARG_LONGITUDE + '=' + coord.getLongitude() + '&' + ARG_ZOOM + '='
					+ DEFAULT_ZOOM + '&' + ARG_ADDRESSDETAILS + '=' + DEFAULT_ADDRESSDETAILS);
			setPost(false);
		}

		public ServiceRequest(Query query) {
			setUrl(BASEURL + QUERY + '?' + ARG_FORMAT + '=' + FORMAT_JSON + '&' + ARG_QUERY + '=' + query.getQuery()
					+ '&' + ARG_ZOOM + '=' + DEFAULT_ZOOM + '&' + ARG_ADDRESSDETAILS + '=' + DEFAULT_ADDRESSDETAILS);
			setPost(false);
		}

		protected void readResponse(InputStream input) throws IOException {
		}

		public LookupLocation getLocation() {
			return location;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.location.LookupProvider#getLocation(java.lang.String)
	 */
	public LookupLocation getLocation(LookupSubject subject) {
		if ((subject instanceof Coordinate) == false) {
			LookupLocation loc = new LookupLocation();
			loc.setLookupStatus(LookupLocation.STATUS_INVALID);
			loc.setLookupStatusMessage("This provider only accepts subject" + Coordinate.class);
			return loc;
		}
		return getLocation((Coordinate) subject);
	}

	public LookupLocation getLocation(Coordinate address) {
		ServiceRequest request = new ServiceRequest(address);
		NetworkManager.getInstance().addToQueueAndWait(request);
		return request.getLocation();
	}
}
