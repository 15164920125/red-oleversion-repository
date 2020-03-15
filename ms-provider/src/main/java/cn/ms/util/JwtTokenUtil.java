///** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
//package ins.platform.auth.jwt.util;
//
//import ins.framework.web.util.RequestUtils;
//import ins.platform.auth.jwt.vo.JwtUser;
//import ins.platform.sysuser.po.SysUser;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.impl.DefaultClaims;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtTokenUtil implements Serializable {
//	private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
//	private static final int MILLISECOND = 1000;
//	private static final long serialVersionUID = -3301605591108950415L;
//	private static final String CLAIM_KEY_USER = "user";
//	private static final String CLAIM_KEY_USERCODE = "userCode";
//	private static final String CLAIM_KEY_USERNAME = "userName";
//	private static final String CLAIM_KEY_CREATED = "created";
//	@Value("${jwt.secret}")
//	private String secret;
//	@Value("${jwt.header}")
//	private String header;
//	@Value("${jwt.expiration}")
//	private Long expiration;
//	@Value("${jwt.tokenHead}")
//	private String tokenHead;
//
//	public Object getAttribute(String attribute) {
//		HttpServletRequest req = RequestUtils.getHttpServletRequest();
//		String authHeader = req.getHeader(this.header);
//		String authToken = authHeader.substring(this.tokenHead.length());
//		Claims claims = this.getClaimsFromToken(authToken);
//		return claims.get(attribute);
//	}
//
//	public String generateToken(SysUser user, Map<String, Object> props) {
//		HashMap claims = new HashMap();
//		claims.putAll(props);
//		claims.put("userCode", user.getUserCode());
//		claims.put("userName", user.getUserName());
//		claims.put("user", user);
//		claims.put("created", new Date());
//		return this.generateToken(claims);
//	}
//
//	public String getUserCode() {
//		HttpServletRequest req = RequestUtils.getHttpServletRequest();
//		String authHeader = req.getHeader(this.header);
//		String authToken = authHeader.substring(this.tokenHead.length());
//		return this.getUserCodeFromToken(authToken);
//	}
//
//	public String getUserCodeFromToken(String token) {
//		String username;
//		try {
//			Claims e = this.getClaimsFromToken(token);
//			username = (String) e.get("userCode");
//		} catch (Exception arg3) {
//			username = null;
//			log.warn("{}", arg3.getMessage(), arg3);
//		}
//
//		return username;
//	}
//
//	public String getUsernameFromToken(String token) {
//		String username;
//		try {
//			Claims e = this.getClaimsFromToken(token);
//			username = (String) e.get("userName");
//		} catch (Exception arg3) {
//			username = null;
//			log.warn("{}", arg3.getMessage(), arg3);
//		}
//
//		return username;
//	}
//
//	private Date getCreatedDateFromToken(String token) {
//		Date created;
//		try {
//			Claims e = this.getClaimsFromToken(token);
//			created = new Date(((Long) e.get("created")).longValue());
//		} catch (Exception arg3) {
//			created = null;
//			log.warn("{}", arg3.getMessage(), arg3);
//		}
//
//		return created;
//	}
//
//	private Date getExpirationDateFromToken(String token) {
//		Date expirationDate;
//		try {
//			Claims e = this.getClaimsFromToken(token);
//			expirationDate = e.getExpiration();
//		} catch (Exception arg3) {
//			expirationDate = null;
//			log.warn("{}", arg3.getMessage(), arg3);
//		}
//
//		return expirationDate;
//	}
//
//	private Claims getClaimsFromToken(String token) {
//		Object claims;
//		try {
//			claims = (Claims) Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
//		} catch (Exception arg3) {
//			claims = new DefaultClaims();
//			log.warn("{}", arg3.getMessage(), arg3);
//		}
//
//		return (Claims) claims;
//	}
//
//	private Date generateExpirationDate() {
//		return new Date(System.currentTimeMillis() + this.expiration.longValue() * 1000L);
//	}
//
//	private Boolean isTokenExpired(String token) {
//		Date expirationDate = this.getExpirationDateFromToken(token);
//		return expirationDate == null ? Boolean.valueOf(false) : Boolean.valueOf(expirationDate.before(new Date()));
//	}
//
//	private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//		return Boolean.valueOf(lastPasswordReset != null && created.before(lastPasswordReset));
//	}
//
//	private String generateToken(Map<String, Object> claims) {
//		return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate())
//				.signWith(SignatureAlgorithm.HS512, this.secret).compact();
//	}
//
//	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//		Date created = this.getCreatedDateFromToken(token);
//		return Boolean.valueOf(!isCreatedBeforeLastPasswordReset(created, lastPasswordReset).booleanValue()
//				&& !this.isTokenExpired(token).booleanValue());
//	}
//
//	public String refreshToken(String token) {
//		String refreshedToken;
//		try {
//			Claims e = this.getClaimsFromToken(token);
//			e.put("created", new Date());
//			refreshedToken = this.generateToken(e);
//		} catch (Exception arg3) {
//			refreshedToken = null;
//			log.warn("{}", arg3.getMessage(), arg3);
//		}
//
//		return refreshedToken;
//	}
//
//	public Boolean validateToken(String token, JwtUser userDetails) {
//		String username = this.getUsernameFromToken(token);
//		Date created = this.getCreatedDateFromToken(token);
//		return Boolean.valueOf(username.equals(userDetails.getUsername()) && !this.isTokenExpired(token).booleanValue()
//				&& !isCreatedBeforeLastPasswordReset(created, userDetails.getLastPasswordResetDate()).booleanValue());
//	}
//}