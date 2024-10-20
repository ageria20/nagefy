package ageria.nagefy.security;


import ageria.nagefy.entities.Client;
import ageria.nagefy.entities.Staff;
import ageria.nagefy.entities.User;
import ageria.nagefy.enums.Role;
import ageria.nagefy.exceptions.NotFoundException;
import ageria.nagefy.exceptions.UnauthorizedException;
import ageria.nagefy.services.ClientsService;
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
    ClientsService clientsService;

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
        String role = this.jwtTools.extractRoleFromToken(accessToken);
        System.out.printf("ROLE: " + role);
        if (role.equals("USER")) {
            Client clientFromDB = this.clientsService.findById(UUID.fromString(id));
            if(clientFromDB == null){
                throw new NotFoundException("CLIENT NOT FOUND");
            }
            Authentication userAuth = new UsernamePasswordAuthenticationToken(clientFromDB, null, clientFromDB.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(userAuth);
        } else if (role.equals("EMPLOYEE")) {
            Staff staffFromDB = this.staffsService.findById(UUID.fromString(id));
            if(staffFromDB == null){
                throw new NotFoundException("STAFF NOT FOUND");
            }
            Authentication staffAuth = new UsernamePasswordAuthenticationToken(staffFromDB, null, staffFromDB.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(staffAuth);
        } else if (role.equals("ADMIN")) {
            User adminFromDB = this.usersService.findById(UUID.fromString(id));
            if(adminFromDB == null){
                throw new NotFoundException("USER NOT FOUND");
            }
            Authentication adminAuth = new UsernamePasswordAuthenticationToken(adminFromDB, null, adminFromDB.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(adminAuth);
        } else {
            throw new UnauthorizedException("Ruolo non autorizzato");
        }

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
