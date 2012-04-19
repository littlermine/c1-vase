/**
 * 
 */
package ca.coolman.auth.oauth1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import com.codename1.io.Externalizable;

/**
 * @author ecoolman
 * 
 */
public class AccessToken implements Token, Externalizable {
	public static final String OBJECT_ID = "access_token";
	static final String USER_ID = "user_id";
	static final String SCREEN_NAME = "screen_name";
	private String token;
	private String secret;
	private long id;
	private String screenName;

	public AccessToken() {
		this(null, null, -1, null);
	}

	/**
	 * @param token
	 * @param secret
	 * @param id
	 * @param screenName
	 */
	AccessToken(String token, String secret, long id,
			String screenName) {
		super();
		this.token = token;
		this.secret = secret;
		this.id = id;
		this.screenName = screenName;
	}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @return the Secret
	 */
	public String getSecret() {
		return secret;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	public void applyParameters(Hashtable target) {
	}

	public void externalize(DataOutputStream os) throws IOException {
		os.writeUTF(getToken());
		os.writeUTF(getSecret());
		os.writeLong(getId());
		os.writeUTF(getScreenName());
	}

	public String getObjectId() {
		return OBJECT_ID;
	}

	public int getVersion() {
		return 1;
	}

	public void internalize(int version, DataInputStream is) throws IOException {
		token = DataInputStream.readUTF(is);
		secret = DataInputStream.readUTF(is);
		id = is.readLong();
		screenName = DataInputStream.readUTF(is);
	}
	
	public void read(Hashtable h) {
		this.token = (String) h.get(TOKEN);
		this.secret = (String) h.get(TOKEN_SECRET);
		this.id = Long.parseLong((String) h.get(USER_ID));
		this.screenName = (String) h.get(SCREEN_NAME);
	}
}
