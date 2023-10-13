package br.com.caina.todolist.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.caina.todolist.user.IUserInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//CLASSE FILTRO, PARA VALIDAR AS PRÉ REQUISIÇOES - LOGINS, TOKEN..
@Component // indica que o spring deve gerenciar
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserInterface userInterface; // REPOSITORY

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // VALIDAR ROTA
        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/tasks/")) {
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecoder = java.util.Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecoder);
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            var user = this.userInterface.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("userId", user.getId());
                    filterChain.doFilter(request, response); // VALIDAR TOKEN
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response); // VALIDAR TOKEN
        }
    }
}
