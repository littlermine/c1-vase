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
package ca.coolman.ui.util;

import com.codename1.ui.Display;

/**
 * Determine resolution class of device based on display characteristics, and
 * select best size for various components based on device resolution class.
 * 
 * @author Eric Coolman
 *
 */
public class Resolution {
	public final static int VERY_LOW = 0;
	public final static int LOW = 1;
	public final static int MEDIUM = 2;
	public final static int HIGH = 3;
	public final static int VERY_HIGH = 4;
	public final static int HD = 5;
	private final static long LIMIT_VERY_LOW = 43340L;
	private final static long LIMIT_LOW = 101000L;
	private final static long LIMIT_MEDIUM = 172800L;
	private final static long LIMIT_HIGH = 409920L;
	private final static long LIMIT_VERY_HIGH = 1036800L;
	private final static long LIMIT_HD = 2073600L;
	private final static int iconSize[] = { 16, 24, 32, 48, 64, 64 };
	private static Resolution _instance;
	private int deviceResolution = -1;
	
	public static Resolution getInstance() {
		if (_instance == null) {
			_instance = new Resolution();
		}
		return _instance;
	}
	
	public int getDeviceResolution() {
		if (deviceResolution == -1) {
			Display d = Display.getInstance();
			long actual = d.getDisplayHeight() * d.getDisplayWidth();
			if (actual <= LIMIT_VERY_LOW) {
				deviceResolution = VERY_LOW;
			} else if (actual <= LIMIT_LOW) {
				deviceResolution = LOW;
			} else if (actual <= LIMIT_MEDIUM) {
				deviceResolution = MEDIUM;
			} else if (actual <= LIMIT_HIGH) {
				deviceResolution = HIGH;
			} else if (actual <= LIMIT_VERY_HIGH) {
				deviceResolution = VERY_HIGH;
			} else if (actual <= LIMIT_HD) {
				deviceResolution = HD;
			} else {
				deviceResolution = HD;
			}
		}
		return deviceResolution;
	}
	
	public int getPreferredIconSize() {
		return iconSize[getDeviceResolution()];
	}
}
