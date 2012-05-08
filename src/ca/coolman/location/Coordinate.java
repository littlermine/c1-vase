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
import com.codename1.maps.Coord;

/**
 * Wraps various styles of specifyng geo coordinates.
 * 
 * @author Eric Coolman
 *
 */
public class Coordinate implements LookupSubject {
	private Coord coord;

	/**
	 * @param coord
	 */
	public Coordinate(Location location) {
		this(location.getLatitude(), location.getLongtitude());
	}

	/**
	 * @param coord
	 */
	public Coordinate(Coord coord) {
		this.coord = coord;
	}

	public Coordinate(double latitude, double longitude) {
		this.coord = new Coord(latitude, longitude);
	}

	/**
	 * @return the coord
	 */
	public Coord getCoord() {
		return coord;
	}
	
	public double getLatitude() {
		return coord.getLatitude();
	}

	public double getLongitude() {
		return coord.getLongitude();
	}
}
