package com.usermanagementservice.authsecurity;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.usermanagementservice.authModel.UserDetail;
import com.usermanagementservice.constants.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * THe class JWTUtil.
 * 
 * @author harsh.jain
 *
 */
@Component
public class JWTUtil {

	/**
	 * The secret.
	 */
	@Value("${springbootwebfluxjjwt.jjwt.secret}")
	private String secret;

	/**
	 * The expirationTime.
	 */
	@Value("${springbootwebfluxjjwt.jjwt.expiration}")
	private String expirationTime;

	/**
	 * The key.
	 */
	private Key key;

	/**
	 * The init.
	 */
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	/**
	 * The getAllClaimsFromToken.
	 * 
	 * @param String token .
	 * 
	 * @return Claims.
	 */
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	/**
	 * The getUsernameFromToken.
	 * 
	 * @param String token .
	 * 
	 * @return String.
	 */
	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	/**
	 * The getExpirationDateFromToken.
	 * 
	 * @param String token .
	 * 
	 * @return Date.
	 */
	public Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	/**
	 * The isTokenExpired.
	 * 
	 * @param String token .
	 * 
	 * @return Boolean.
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * The generateToken.
	 * 
	 * @param UserDetail userDetail .
	 * 
	 * @return String.
	 */
	public String generateToken(UserDetail userDetail) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(Constants.ROLE, userDetail.getRoles());
		return doGenerateToken(claims, userDetail.getUsername());
	}

	/**
	 * The doGenerateToken.
	 * 
	 * @param String claims .
	 * 
	 * @param String username .
	 * 
	 * @return String.
	 */
	private String doGenerateToken(Map<String, Object> claims, String username) {
		Long expirationTimeLong = Long.parseLong(expirationTime);

		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(key).compact();
	}

	/**
	 * The validateToken.
	 * 
	 * @param String token .
	 * 
	 * @return Boolean.
	 */
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

}
