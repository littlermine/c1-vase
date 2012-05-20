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
import com.codename1.maps.Coord;

/**
 * A reverse geocoder implementation using Google Maps provider.
 * 
 * @author ecoolman
 * 
 */
public class GoogleReverseGeocoderProvider implements LookupProvider {
	private final static String BASEURL = "http://maps.googleapis.com/maps/api/geocode/json";
	private final static String ARG_LATLNG = "latlng";
	private final static String ARG_SENSOR = "sensor";

	class ServiceRequest extends ConnectionRequest {
		private LookupLocation location;

		public ServiceRequest(Coord coord) {
			setUrl(BASEURL + '?' + ARG_SENSOR + '=' + "true&" + ARG_LATLNG + '=' + coord.getLatitude() + ','
					+ coord.getLongitude());
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
		ServiceRequest request = new ServiceRequest(address.getCoord());
		NetworkManager.getInstance().addToQueueAndWait(request);
		return request.getLocation();
	}
}
