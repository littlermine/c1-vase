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

import com.codename1.location.Location;
import com.codename1.location.LocationManager;

/**
 * Extends Codename One's location object, adding several geo fields typical of a lookup.
 * 
 * @author Eric Coolman
 *
 */
public class LookupLocation extends Location {
	public final static String STATUS_OK = "OK";
	public final static String STATUS_INVALID = "INVALID";
	private String lookupStatus;
	private String lookupStatusMessage;
	private String IPAddress;
	private String countryCode;
	private String countryName;
	private String regionName;
	private String cityName;
	private String postalCode;
	private String timezoneOffset;

	/**
	 * @return the lookupStatus
	 */
	public String getLookupStatus() {
		return lookupStatus;
	}

	/**
	 * @param lookupStatus the lookupStatus to set
	 */
	public void setLookupStatus(String lookupStatus) {
		this.lookupStatus = lookupStatus;
		if ("OK".equals(lookupStatus)) {
			this.lookupStatusMessage = null;
			setStatus(LocationManager.AVAILABLE);
		} else {
			setStatus(LocationManager.TEMPORARILY_UNAVAILABLE);
		}
	}


	/**
	 * @return the lookupStatusMessage
	 */
	public String getLookupStatusMessage() {
		return lookupStatusMessage;
	}


	/**
	 * @param lookupStatusMessage the lookupStatusMessage to set
	 */
	public void setLookupStatusMessage(String lookupStatusMessage) {
		this.lookupStatusMessage = lookupStatusMessage;
	}


	/**
	 * @return the iPAddress
	 */
	public String getIPAddress() {
		return IPAddress;
	}


	/**
	 * @param iPAddress the iPAddress to set
	 */
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}


	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}


	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}


	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}


	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}


	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}


	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}


	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the timezoneOffset
	 */
	public String getTimezoneOffset() {
		return timezoneOffset;
	}


	/**
	 * @param timezoneOffset the timezoneOffset to set
	 */
	public void setTimezoneOffset(String timezoneOffset) {
		this.timezoneOffset = timezoneOffset;
	}
	

}
