package com.usermanagementservice.authsecurity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.constants.LoggerConstants;

/**
 * The class CustumPasswordEncoder.
 * 
 * @author harsh.jain
 *
 */
@Component
public class CustumPasswordEncoder implements PasswordEncoder {

	/**
	 * The secret.
	 */
	@Value("${springbootwebfluxjjwt.password.encoder.secret}")
	private String secret;

	/**
	 * The iteration.
	 */
	@Value("${springbootwebfluxjjwt.password.encoder.iteration}")
	private Integer iteration;

	/**
	 * The keylength.
	 */
	@Value("${springbootwebfluxjjwt.password.encoder.keylength}")
	private Integer keylength;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CustumPasswordEncoder.class);

	/**
	 * The encode.
	 * 
	 * CharSequenece cs.
	 */
	@Override
	public String encode(CharSequence cs) {
		logger.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.PASSWORD_ENCODER);
		try {
			byte[] result = SecretKeyFactory.getInstance(Constants.PASSCODEKEY)
					.generateSecret(
							new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
					.getEncoded();
			return Base64.getEncoder().encodeToString(result);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			logger.error(LoggerConstants.ERROR_OCCURED_WHILE_ENCODE_PASSWORD);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * The matches.
	 * 
	 * CharSequenece cs.
	 * 
	 * String string.
	 */
	@Override
	public boolean matches(CharSequence cs, String string) {
		return encode(cs).equals(string);
	}

}
