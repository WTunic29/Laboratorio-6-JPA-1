package co.edu.unipiloto.laboratorio6jpa.services;

import com.example.models.Competitor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author poeta
 */
@Path("login")
public class LoginService {
    
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    @PersistenceContext(unitName = "CompetitorsPU")
    private EntityManager entityManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> credentials) {

        if (credentials == null || credentials.get("email") == null || credentials.get("password") == null) {

            throw new NotAuthorizedException(
                    "Correo electrónico y contraseña son obligatorios"
            );
            
        }

        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            
            Competitor competitor = entityManager.createQuery(
                    "SELECT c FROM Competitor c "
                    + "WHERE c.address = :email "
                    + "AND c.password = :password",
                    Competitor.class
            )
            .setParameter("email", email)
            .setParameter("password", password)
            .getResultStream()
            .findFirst()
            .orElse(null);

            if (competitor == null) {
                
                throw new NotAuthorizedException(
                        "Credenciales incorrectas"
                );
                
            }

            // No exponer la contraseña en la respuesta.
            return Response.ok(
                    
                    Map.of(
                            "message", "Inicio de sesión exitoso",
                            "id", competitor.getId(),
                            "name", competitor.getName(),
                            "surname", competitor.getSurname(),
                            "email", competitor.getAddress()
                    )).build();

        } catch (NotAuthorizedException e) {
            
            throw e;

        } catch (Exception e) {
            
            LOGGER.log( Level.SEVERE, "Error al procesar el inicio de sesión", e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "No se pudo procesar el inicio de sesión")).build();
            
        }
        
    }
    
}
