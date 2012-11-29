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
package ca.coolman.maps.layers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import com.codename1.components.WebBrowser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.Util;
import com.codename1.io.services.ImageDownloadService;
import com.codename1.maps.Coord;
import com.codename1.maps.layers.PointLayer;
import com.codename1.maps.layers.PointsLayer;
import com.codename1.processing.Result;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.xml.Element;

/**
 * A PointLayer implementation for CodenameOne that loads it's data from a KML file.
 * 
 * @author Eric Coolman
 * 
 */
public class KMLPointsLayer extends PointsLayer {
	private final static String DEFAULT_PIN = "http://maps.google.com/mapfiles/ms/micons/red-pushpin.png";
	private Hashtable descriptions;

	public KMLPointsLayer(InputStream kml) throws IllegalArgumentException, IOException {
		this(Result.fromContent(kml, Result.XML));
	}

	public KMLPointsLayer(String kml) {
		this(Result.fromContent(kml, Result.XML));
	}

	private String getDescription(String name) {
		String d = (String)descriptions.get(name);
		if (d == null) {
			return null;
		}
		return "<html><body>"+d+"</body></html>";
	}
	
	private void setMarkerIcon(final PointLayer pl, Result kml, String name) {
		if (name.startsWith("#")) {
			name = name.substring(1);
		}
		String path = "//Style[@id='" + name + "']/IconStyle/Icon/href";
		String url = kml.getAsString(path);
		if (url == null) {
			url = DEFAULT_PIN;
		}
		System.out.println("url: " + url);
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Image i = (Image)((NetworkEvent)event).getMetaData();
				System.out.println("Setting icon for point: " + pl.getName());
				pl.setIcon(i);
			}
		};
		ImageDownloadService.createImageToStorage(url, al, name + "-mapmarker");
	}

	private Coord getMarkerCoordinates(Result marker) {
		String s = marker.getAsString("Point/coordinates");
		String coords[];
		if (s == null || (coords = Util.split(s, ",")).length < 2) {
			return null;
		}
		try {
			// kml uses lng,lat where most use lat,lng
			double lat = Double.parseDouble(coords[1]);
			double lng = Double.parseDouble(coords[0]);
			return new Coord(lat, lng);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	KMLPointsLayer(Result kml) {
		descriptions = new Hashtable();
		Vector v = kml.getAsArray("//Placemark");
		for (int i = 0; i < v.size(); i++) {
			Result marker = Result.fromContent(((Element) v.elementAt(i)).getChildAt(0));
			System.out.println("Name:" + marker.getAsString("name"));
			Coord coords = getMarkerCoordinates(marker);
			if (coords != null) {
				String name = marker.getAsString("name");
				String desc = marker.getAsString("description");
				if (desc != null) {
					descriptions.put(name, desc);
				}
				PointLayer p = new PointLayer(coords, name, null);
				p.setDisplayName(true);
				String style = marker.getAsString("styleUrl");
				if (style == null) {
					style = "default";
				}
				setMarkerIcon(p, kml, style);
				addPoint(p);
			}
		}
		addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                PointLayer p = (PointLayer) evt.getSource();
                System.out.println("pressed " + p);
                String name = p.getName();
                final Dialog d = new Dialog(name);
                d.setLayout(new BorderLayout());
                WebBrowser wb = new WebBrowser();
                wb.setPage(getDescription(name), "http://localhost");
                d.addComponent(BorderLayout.CENTER, wb);
                d.setDisposeWhenPointerOutOfBounds(true);
                Command backCommand = new Command("Back") {
					public void actionPerformed(ActionEvent evt) {
						d.dispose();
					}
                };
                d.addCommand(backCommand);
                d.setBackCommand(backCommand);
                d.show(1, 1, 1, 1);
            }
        });				
	}
}
