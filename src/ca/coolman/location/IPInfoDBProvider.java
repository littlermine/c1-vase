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
import java.io.InputStreamReader;
import java.util.Hashtable;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.location.LocationManager;

/**
 * Get API key for IPInfoDB service here:  
 * <pre>http://ipinfodb.com/register.php</pre>
 * 
 * @author Eric Coolman
 * 
 */
public class IPInfoDBProvider implements LookupProvider {
	private final static String ENDPOINT = "http://api.ipinfodb.com/v3/ip-city/";
	private final static String ARG_FORMAT = "format";
	private final static String ARG_APIKEY = "key";
	private final static String ARG_IPADDRESS = "ip";
	private final static String FORMAT_JSON = "json";
	private String apiKey;

	class ServiceRequest extends ConnectionRequest {
		private LookupLocation location;
		public ServiceRequest(String ipAddress) {
			setUrl(ENDPOINT + '?' + ARG_FORMAT + '=' + FORMAT_JSON + '&' + ARG_APIKEY + '=' + apiKey + '&'
					+ ARG_IPADDRESS + '=' + ipAddress);
			setPost(false);
		}

		protected void readResponse(InputStream input) throws IOException {
			JSONParser parser = new JSONParser();
			Hashtable response = parser.parse(new InputStreamReader(input));
			LookupLocation loc = new LookupLocation();
			loc.setLookupStatus((String) response.get("statusCode"));
			if (loc.getStatus() == LocationManager.AVAILABLE) {
				loc.setLookupStatusMessage((String) response.get("statusMessage"));
			} else {
				loc.setIPAddress((String) response.get("ipAddress"));
				loc.setCountryCode((String) response.get("countryCode"));
				loc.setCountryName((String) response.get("countryName"));
				loc.setRegionName((String) response.get("regionName"));
				loc.setCityName((String) response.get("cityName"));
				loc.setPostalCode((String) response.get("zipCode"));
				loc.setTimezoneOffset((String) response.get("timeZone"));
				try {
					loc.setLatitude(Double.parseDouble((String) response.get("latitude")));
					loc.setLongtitude(Double.parseDouble((String) response.get("longitude")));
				} catch (NumberFormatException nfe) {
					Log.e(nfe);
					loc.setLookupStatus(LookupLocation.STATUS_INVALID);
					loc.setLookupStatusMessage("Service responded with an invalid coordinate");
				}
			}
			this.location = loc;
		}

		public LookupLocation getLocation() {
			return location;
		}
	}

	/**
	 * 
	 * @param apiKey API key from http://ipinfodb.com/register.php
	 */
	public IPInfoDBProvider(String apiKey) {
		this.apiKey = apiKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.coolman.location.LookupProvider#getLocation(java.lang.String)
	 */
	public LookupLocation getLocation(LookupSubject subject) {
		if ((subject instanceof IPAddress) == false) {
			LookupLocation loc = new LookupLocation();
			loc.setLookupStatus(LookupLocation.STATUS_INVALID);
			loc.setLookupStatusMessage("This provider only accepts subject" + IPAddress.class);
			return loc;
		}
		return getLocation((IPAddress)subject);
	}
	
	public LookupLocation getLocation(IPAddress address) {
		ServiceRequest request = new ServiceRequest(address.getIPAddress());
		NetworkManager.getInstance().addToQueueAndWait(request);
		return request.getLocation();
	}
}
