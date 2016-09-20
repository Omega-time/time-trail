package paysafe.interns.config;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;

import paysafe.interns.models.UserInfo;
import paysafe.interns.repositories.UserRepository;

public class RequestFilter extends OncePerRequestFilter {
    private UserRepository userRepository;

    public RequestFilter(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(request.getRequestURI().startsWith("/api")) {
            String accessToken = request.getHeader("Authorization");
            if (accessToken != null) {
                accessToken = accessToken.replaceFirst("Bearer ", "");
                UserInfo cachedUser = (UserInfo) request.getSession().getAttribute("user");
                if (cachedUser == null || !cachedUser.getAccessToken().equals(accessToken)) {
                    try {
                        Tokeninfo tokeninfo = getTokenInfo(accessToken);
                        UserInfo googleUser = parseUserFromTokenInfo(tokeninfo);
                        googleUser.setAccessToken(accessToken);
                        cacheUser(request, googleUser);
                    } catch (GoogleJsonResponseException | GeneralSecurityException e) {
                        try {
                            JSONObject errorResponse = new JSONObject();
                            errorResponse.put("statusText", "Authorization error. Possibly invalid access token.");
                            errorResponse.put("status", 403);
                            response.getWriter().write(errorResponse.toString());
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                        } catch (JSONException e1) {
                        }
                        return;
                    }
                }
                filterChain.doFilter(request, response);
            } else {
                response.sendError(403, "Forbidden");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private Tokeninfo getTokenInfo(String accessToken) throws GeneralSecurityException, IOException {
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

        Oauth2 oauth2 = new Oauth2.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
        ).build();
        return oauth2.tokeninfo().setAccessToken(accessToken).execute();
    }

    private UserInfo parseUserFromTokenInfo(Tokeninfo tokeninfo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(tokeninfo.toString(), UserInfo.class);
    }

    private void cacheUser(HttpServletRequest request, UserInfo googleUser) {
        if (userRepository.findOne(googleUser.getId()) == null) {
            userRepository.save(googleUser);
        }
        request.getSession().setAttribute("user", googleUser);
    }
}
