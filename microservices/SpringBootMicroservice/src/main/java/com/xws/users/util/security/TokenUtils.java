package com.xws.users.util.security;

import com.xws.users.users.model.roles.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

// Utility class for working with JSON Web Tokens
@Component
public class TokenUtils {

    // Token Publisher
    @Value("centralized-pharmacy-information-system")
    private String APP_NAME;

    // The secret that only a backend application needs to know in order to be able to generate and check JWT https://jwt.io/
    @Value("bajo-is-one-of-us")
    public String SECRET;

    // Period of validity
    @Value("3000000")
    private int EXPIRES_IN;

    // The name of the header through which the JWT will be forwarded in the server-client communication
    @Value("Authorization")
    private String AUTH_HEADER;

    // It is possible to generate JWT for different clients (eg web and mobile clients will not have the same JWT duration, JWT for mobile clients will last longer because the application may be used less often in this way)    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    // Algorithm for signing JWT
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    // Function to generate JWT token
    public String generateToken(UserAccount user) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(user.getUsername())
                .setAudience(generateAudience())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .claim("role", user.getRole())
                // .claim("key", value) // It's possible to place arbitrary data in the body of the JWT token
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private String generateAudience() {
        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    // Function to refresh JWT tokens
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(new Date());
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getIssuedAtDateFromToken(token);
        return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
                && (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
    }

    // JWT token validation function
    public Boolean validateToken(String token, UserDetails userDetails) {
        UserAccount user = (UserAccount) userDetails;
        final String email = getEmailFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        return (email != null && email.equals(userDetails.getUsername())
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    public String getEmailFromToken(String token) {
        String email;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            email = claims.getSubject();
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    // Function to retrieve JWT tokens from request
    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);

        // The JWT is passed through the Authorization header in the format:
        // Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = this.getAudienceFromToken(token);
        return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
    }

    // Function for reading all data from JWT token
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

}