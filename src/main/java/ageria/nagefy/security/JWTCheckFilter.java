package ageria.nagefy.security;


import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.User;
import ageria.nagefy.enums.Role;
import ageria.nagefy.exceptions.UnauthorizedException;
import ageria.nagefy.services.StaffsService;
import ageria.nagefy.services.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.Authenticator;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ageria.nagefy.enums.Role.ADMIN;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    @Autowired
    JWTTools jwtTools;

    @Autowired
    UsersService usersService;

    @Autowired
    StaffsService staffsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new UnauthorizedException("PROBLEMS WITH TOKEN");
        }
        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
        String id = this.jwtTools.extractIdFromToken(accessToken);
        User userFromDB = this.usersService.findById(UUID.fromString(id));
        Authentication userAuth = new UsernamePasswordAuthenticationToken(userFromDB, null, userFromDB.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(userAuth);

        filterChain.doFilter(request, response);
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        List<String> patternList = Arrays.asList("/auth/**", "/clients/reset","/staffs/reset");
        AntPathMatcher newAntPath = new AntPathMatcher();
        return patternList.stream().anyMatch(pattern -> newAntPath.match(pattern, path));
    }

}
