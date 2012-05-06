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
package ca.coolman.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Vector;

import ca.coolman.io.BufferedReader;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;

/**
 * A utility class for determining mime types of files. This implementation uses
 * the latest public domain mime-types registry list at
 * http://svn.apache.org/repos/asf/httpd/httpd/branches/1.3.x/conf/mime.types by
 * default, but can be configured to read the mime-type registry locally.
 * 
 * @author Eric Coolman
 * 
 */
public class MimeTypes {
	private static final String URL = "http://svn.apache.org/repos/asf/httpd/httpd/branches/1.3.x/conf/mime.types";
	public static final String DEFAULT_MIMETYPE = "application/octet-stream";
	private static MimeTypes _instance;
	private Hashtable map;

	/**
	 * Construct an instance, used internally, this class is a singleton.
	 */
	private MimeTypes() {
		map = new Hashtable();
		load();
	}

	/**
	 * Construct an instance with a given input stream, used internally, this
	 * class is a singleton.
	 */
	private MimeTypes(InputStream is) {
		map = new Hashtable();
		read(is);
	}

	/**
	 * Load the mimetype registry from the Apache mime-type list.
	 * 
	 * <pre>
	 * See http://svn.apache.org/repos/asf/httpd/httpd/branches/1.3.x/conf/mime.types.
	 * </pre>
	 */
	private void load() {
		ConnectionRequest req = new ConnectionRequest() {
			protected void readResponse(InputStream input) throws IOException {
				read(input);
			}
		};
		req.setPost(false);
		req.setUrl(URL);
		NetworkManager.getInstance().addToQueueAndWait(req);
	}

	/**
	 * Parse a line from the Apache registry to retrieve a list containing the
	 * mime-type id, followed by the file extensions associacted to it.
	 * 
	 * @param in a line from the apache registry document.
	 * @return a list containing the mime-type id, followed by the file
	 *         extensions associacted to it.
	 */
	protected String[] getTokens(String in) {
		String line = in.replace('\t', ' ');
		String tokens[] = Util.split(line, " ");
		Vector vector = new Vector();
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i].trim();
			if (token.length() > 0) {
				vector.addElement(token);
			}
		}
		tokens = new String[vector.size()];
		vector.copyInto(tokens);
		return tokens;
	}

	/**
	 * Read the mime-type registry document.
	 * 
	 * @param is input stream of mime-type registry document.
	 */
	protected void read(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				String tokens[] = getTokens(line);
				if (tokens.length > 1) {
					for (int i = 1; i < tokens.length; i++) {
						map.put(tokens[i], tokens[0]);
						System.err.println("tokens[" + i + "] - [" + tokens[i] + "]=[" + tokens[0] + "]");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Use this method before getInstance() to provide a local registry of
	 * mime-types.
	 * 
	 * Override the mimetype registry with the contents of inputstream, must
	 * match the format of the data at:
	 * 
	 * http://svn.apache.org/repos/asf/httpd/httpd/branches/1.3.x/conf/mime.
	 * types
	 * 
	 * @param is
	 * @return
	 */
	public static final void initializeWith(InputStream is) {
		_instance = new MimeTypes(is);
	}

	/**
	 * Get a MimeTypes instance.
	 * 
	 * @return
	 */
	public static final MimeTypes getInstance() {
		if (_instance == null) {
			_instance = new MimeTypes();
		}
		return _instance;
	}

	/**
	 * Determine the mime type associated with the given filename. This
	 * implementation works specifically off the file extension, no reading of
	 * magic bytes in the file.
	 * 
	 * @return the mime-type of the filename, or the default
	 *         application/octet-stream if the registry does not contain a
	 *         match.
	 * @see #DEFAULT_MIMETYPE
	 */
	public String getMimeType(String filename) {
		return getMimeType(filename, DEFAULT_MIMETYPE);
	}

	/**
	 * Determine the mime type associated with the given filename. This
	 * implementation works specifically off the file extension, no reading of
	 * magic bytes in the file.
	 * 
	 * @return the mime-type of the filename, or the default if the registry
	 *         does not contain a match.
	 */
	public String getMimeType(String filename, String defaultType) {
		int index = filename.lastIndexOf('.');
		if (index == -1) {
			return defaultType;
		}
		String extension = filename.substring(index + 1).toLowerCase();
		if (map.containsKey(extension) == false) {
			return defaultType;
		}
		return (String) map.get(extension);
	}
}
