package io.agileintellignece.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static io.agileintellignece.ppmtool.security.SecurityConstant.EXPIRATION_TIME;
import static io.agileintellignece.ppmtool.security.SecurityConstant.SECRET;

import io.agileintellignece.ppmtool.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date date = new Date(System.currentTimeMillis());

		Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
		String userId = Long.toString(user.getId());
		Map<String, Object> claims = new HashMap();
		claims.put("id", (Long.toString(user.getId())));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());

		return Jwts.builder().setSubject(userId).setClaims(claims).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			System.out.println("Invalid JWT Signature");
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT Token");
		} catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT Signature");
		} catch (IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty");
		}
		return false;
	}

	public Long getUserIdFromJWT(String token)
	{
		Claims claims= Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String) claims.get("id");
		
		return Long.parseLong(id);
	}
	
	
	
}
