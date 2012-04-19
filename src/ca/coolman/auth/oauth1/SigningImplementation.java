/**
 * 
 */
package ca.coolman.auth.oauth1;


/**
 * @author ecoolman
 *
 */
public abstract class SigningImplementation {
	public abstract String getId();
	
	protected abstract byte[] createSignature(byte[] key, byte[] data);
}
