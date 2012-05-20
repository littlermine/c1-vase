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

import java.util.Hashtable;

/**
 * @author Eric Coolman
 * 
 */
public class LocationLookupService {
	private LookupProvider provider;

	private static Hashtable keys;

	public LocationLookupService(LookupProvider provider) {
		this.provider = provider;
	}

	public LookupLocation getLocation(LookupSubject subject) {
		return provider.getLocation(subject);
	}

	public static LocationLookupService createLookupByIP() {
		String apikey = null;
		if (keys != null && keys.containsKey(IPInfoDBProvider.class)) {
			apikey = (String) keys.get(IPInfoDBProvider.class);
		}
		return new LocationLookupService(new IPInfoDBProvider(apikey));
	}

	public static LocationLookupService createLookupByGeocode() {
		String apikey = null;
		if (keys != null && keys.containsKey(GoogleReverseGeocoderProvider.class)) {
			apikey = (String) keys.get(GoogleReverseGeocoderProvider.class);
		}
		// TODO: update the geocoder to accept key
		return new LocationLookupService(new GoogleReverseGeocoderProvider());
	}
}
