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
package ca.coolman.ads;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import ca.coolman.location.Coordinate;
import ca.coolman.location.IPAddress;
import ca.coolman.location.LocationLookupService;
import ca.coolman.location.LookupLocation;

import com.codename1.ads.AdsService;
import com.codename1.components.Ads;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

/**
 * An Ad service provider implementation. The server implementation will be held
 * in a separate project.
 * 
 * @author Eric Coolman
 * 
 */
public class SelfHostedAdsProvider extends AdsService {
	private static final String API_VERSION = "1.0.0";
	// NOTE: bitfield values
	private static final int DEVICE_TABLET = 1;
	private static final int DEVICE_TOUCHSCREEN = 2;
	private static final int DEVICE_RESERVED2 = 4;
	private static final int DEVICE_RESERVED3 = 8;
	private static final int DEVICE_RESERVED4 = 16;
	private static final int DEVICE_ANDROID = 32;
	private static final int DEVICE_IOS = 64;
	private static final int DEVICE_BLACKBERRY = 128;
	private static final int DEVICE_J2ME = 256;

	// Argument categories
	private static final String ARGCAT_DEVICE = "d";
	private static final String ARGCAT_USER = "u";
	private static final String ARGCAT_APP = "a";
	private static final String ARGCAT_LOCATION = "l";
	private static final String ARGCAT_STATE = "s";

	// Device related arguments
	private static final String ARG_DEVICE = ARGCAT_DEVICE + "d";
	private static final String ARG_WIDTH = ARGCAT_DEVICE + "w";
	private static final String ARG_HEIGHT = ARGCAT_DEVICE + "h";
	private static final String ARG_MIZDEN = ARGCAT_DEVICE + "m";
	private static final String ARG_DEVICEID = ARGCAT_DEVICE + "i";

	// App related arguments
	private static final String ARG_APPID = ARGCAT_APP + "i";
	private static final String ARG_DEBUG = "ARGCAT_APP + x";

	// User related arguments
	private static final String ARG_USERID = ARGCAT_USER + "i";
	private static final String ARG_GENDER = ARGCAT_USER + "g";
	private static final String ARG_AGE = ARGCAT_USER + "a";

	// Location related arguments
	private static final String ARG_LATITUDE = ARGCAT_LOCATION + "a";
	private static final String ARG_LONGITUDE = ARGCAT_LOCATION + "o";
	private static final String ARG_CITY = ARGCAT_LOCATION + "c";
	private static final String ARG_REGION = ARGCAT_LOCATION + "r";
	private static final String ARG_COUNTRY = ARGCAT_LOCATION + "c";

	// State related arguments. Categories and tags can be passed in
	// initial registration, and during state update, both are optional.
	private static final String ARG_PORTRAIT = ARGCAT_STATE + "p";
	private static final String ARG_CATEGORIES = ARGCAT_STATE + "c";
	//private static final String ARG_TAGS = ARGCAT_STATE + "t";

	private static final String ARG_SESSIONID = "s";
	private static final String ARG_VERSION = "v";

	private static final String RESPONSE_KEY_AD = "ad";

	/**
	 * Endpoint URL of the ad service provider.
	 */
	private String endpoint;
	/**
	 * Globally unique user id, as known by the application.
	 */
	private String uid;
	/**
	 * If debugMode is true, the ad will not be real, but all functionality and
	 * attributes will be the same as a true ad. Will not affect quota.
	 */
	private boolean debugMode;
	/**
	 * User location
	 */
	private LookupLocation location;
	/**
	 * User Session id
	 */
	private String sessionId;

	public void initService(Ads adsComponent) {
		if (getLocation() == null) {
			LocationManager lm = LocationManager.getLocationManager();
			Location loc;
			if (lm.getStatus() == LocationManager.AVAILABLE) {
				try {
					loc = lm.getCurrentLocation();
				} catch (IOException e) {
					Log.e(e);
					loc = lm.getLastKnownLocation();
				}
			} else {
				loc = lm.getLastKnownLocation();
			}

			// Use location if provided, otherwise use either reverse IP or
			// Geocode depending on availability of sensor.
			if (adsComponent.getLocation() != null) {
				LookupLocation lloc = new LookupLocation();
				parseLocation(lloc, loc, adsComponent.getLocation());
				setLocation(lloc);
			} else {
				if (loc == null) {
					LocationLookupService lookup = LocationLookupService.createLookupByIP();
					LookupLocation lloc = lookup.getLocation(new IPAddress(null));
					if (lloc.getLookupStatus() == LookupLocation.STATUS_OK) {
						setLocation(lloc);
					}
				} else {
					LocationLookupService lookup = LocationLookupService.createLookupByGeocode();
					LookupLocation lloc = lookup.getLocation(new Coordinate(loc));
					if (lloc.getLookupStatus() != LookupLocation.STATUS_OK) {
						lloc.setLatitude(loc.getLatitude());
						lloc.setLongtitude(loc.getLongtitude());
					}
				}
			}
		}
		setPost(false);
		setUrl(endpoint);
		addArgument(ARG_VERSION, API_VERSION);
		addArgument(ARG_APPID, adsComponent.getAppID());
		String cats = createCategories(adsComponent);
		if (cats != null) {
			addArgument(ARG_CATEGORIES, cats);
		}
		if (isDebugMode()) {
			addArgument(ARG_DEBUG, "1");
		}
		// set device params
		addArgument(ARG_DEVICE, Integer.toString(getDevice()));
		addArgument(ARG_WIDTH, Integer.toString(getWidth()));
		addArgument(ARG_HEIGHT, Integer.toString(getHeight()));
		if (isPortrait()) {
			addArgument(ARG_PORTRAIT, "1");
		} else {
			addArgument(ARG_PORTRAIT, "0");
		}
		String v = getDeviceID();
		if (v != null && v.length() > 0) {
			addArgument(ARG_DEVICEID, v);
		}
		v = getMizden();
		if (v != null && v.length() > 0) {
			addArgument(ARG_MIZDEN, v);
		}
		addLocationArgument();
		v = adsComponent.getAge();
		if (v != null && v.length() > 0) {
			addArgument(ARG_AGE, v);
		}
		v = adsComponent.getGender();
		if (v != null && v.length() > 0) {
			addArgument(ARG_GENDER, v.toLowerCase().substring(0, 0));
		}
		//addArgument(ARG_USERID, adsComponent.);
	}

	
	private void addLocationArgument() {
		LookupLocation loc = getLocation();
		if (loc != null) {
			double d = loc.getLatitude();
			if (d != 0d) {
				addArgument(ARG_LATITUDE, Double.toString(loc.getLatitude()));
				addArgument(ARG_LONGITUDE, Double.toString(loc.getLongtitude()));
			}
			String s = loc.getCountryCode();
			if (s != null && s.length() > 0) {
				addArgument(ARG_COUNTRY, s);
			} else {
				s = loc.getCountryName();
				if (s != null && s.length() > 0) {
					addArgument(ARG_COUNTRY, s);
				}
			}
			s = loc.getRegionName();
			if (s != null && s.length() > 0) {
				addArgument(ARG_REGION, s);
			}
			s = loc.getCityName();
			if (s != null && s.length() > 0) {
				addArgument(ARG_CITY, s);
			}
		}
	}


	private String createCategories(Ads ads) {
		String keywords[] = ads.getKeywords();
		if (keywords == null || keywords.length == 0) {
			return null;
		}
		String k = "";
		for (int i = 0; i < keywords.length; i++) {
			k += "," + keywords[i];
		}
		return k.substring(1);
	}

	private void parseLocation(LookupLocation target, Location sensorLocation, String providedLocation) {
		if (sensorLocation != null) {
			target.setLatitude(sensorLocation.getLatitude());
			target.setLongtitude(sensorLocation.getLongtitude());
		}
		String tokens[] = Util.split(providedLocation, ",");
		if (tokens.length > 0) {
			if (tokens[0].length() == 2) {
				target.setCountryCode(tokens[0]);
			} else {
				target.setCountryName(tokens[0]);
			}
		}
		if (tokens.length > 1) {
			target.setRegionName(tokens[1]);
		}
		if (tokens.length > 2) {
			target.setRegionName(tokens[2]);
		}
	}
	/**
	 * We don't appear to have access to an orientation listener, so we'll need
	 * to check on each ad request.
	 * 
	 * @return
	 */
	protected boolean isPortrait() {
		return Display.getInstance().isPortrait();
	}
	/**
	 * @return the endpoint
	 */
	protected String getEndpoint() {
		return endpoint;
	}
	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	/**
	 * @return the uid
	 */
	protected String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * Get the device mizden.
	 * 
	 * MSISDN (pronounced /'mɪzdən/) is a number uniquely identifying a
	 * subscription in a GSM or a UMTS mobile network. Simply put, it is the
	 * telephone number to the SIM card in a mobile/cellular phone
	 * 
	 * @return the mizden
	 */
	protected String getMizden() {
		return Display.getInstance().getProperty("MSISDN", null);
	}

	/**
	 * @return the debugMode
	 */
	protected boolean isDebugMode() {
		return debugMode;
	}
	
	/**
	 * @param debugMode the debugMode to set
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	/**
	 * @return the device
	 */
	protected int getDevice() {
		String os = Display.getInstance().getPlatformName();
		int device = 0;
		if (Display.getInstance().isTablet()) {
			device |= DEVICE_TABLET;
		}
		if (Display.getInstance().isTouchScreenDevice()) {
			device |= DEVICE_TOUCHSCREEN;
		}
		if (os.equals("and")) {
			device |= DEVICE_ANDROID;
		} else if (os.equals("rim")) {
			device |= DEVICE_BLACKBERRY;
		} else if (os.equals("ios")) {
			device |= DEVICE_IOS;
		} else if (os.equals("me")) {
			device |= DEVICE_J2ME;
		}
		return device;
	}

	/**
	 * @return the device id
	 */
	protected String getDeviceID() {
		if (Display.getInstance().getPlatformName().equals("ios")) {
			return Display.getInstance().getProperty("UDID", null);
		} else {
			return Display.getInstance().getProperty("IMEI", null);
		}
	}

	/**
	 * @return the width
	 */
	protected int getWidth() {
		return Display.getInstance().getDisplayWidth();
	}
	/**
	 * @return the height
	 */
	protected int getHeight() {
		return Display.getInstance().getDisplayHeight();
	}

	/**
	 * Get the user location.
	 * 
	 * @return
	 */
	protected LookupLocation getLocation() {
		return location;
	}
	/**
	 * Set the user location. Use this if the app caches the user location,
	 * otherwise, an attempt to detection the location will be performed.
	 * 
	 * @param location
	 */
	public void setLocation(LookupLocation location) {
		this.location = location;
	}

    protected void readResponse(InputStream input) throws IOException {
    	JSONParser parser = new JSONParser();
    	Hashtable h = parser.parse(new InputStreamReader(input));
    	if (h.containsKey(RESPONSE_KEY_AD)) {
    		fireResponseListener(new ActionEvent(h.get(RESPONSE_KEY_AD)));
    	}
    }
}
