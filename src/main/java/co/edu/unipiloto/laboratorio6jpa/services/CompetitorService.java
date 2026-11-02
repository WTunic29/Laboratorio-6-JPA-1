package co.edu.unipiloto.laboratorio6jpa.services;

import com.example.models.Competitor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author poeta
 */
@Path("competitors")
public class CompetitorService {

    private static final Logger LOGGER
            = Logger.getLogger(CompetitorService.class.getName());

    @PersistenceContext(unitName = "CompetitorsPU")
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Competitor> getCompetitors() {

        return entityManager.createQuery(
                "SELECT c FROM Competitor c",
                Competitor.class
        ).getResultList();
    }

    @GET
@Path("diagnostico")
@Produces(MediaType.APPLICATION_JSON)
public Response diagnostico() {
    try {
        Class<?> claseRecibida = Competitor.class;

        var entidades = entityManager.getMetamodel()
                .getEntities()
                .stream()
                .map(entity -> {
                    Class<?> claseEntidad = entity.getJavaType();

                    return Map.of(
                            "nombre", claseEntidad.getName(),
                            "mismoObjetoClass",
                            claseEntidad == claseRecibida,
                            "mismoClassLoader",
                            claseEntidad.getClassLoader()
                                    == claseRecibida.getClassLoader(),
                            "classLoaderEntidad",
                            String.valueOf(claseEntidad.getClassLoader()),
                            "classLoaderServicio",
                            String.valueOf(claseRecibida.getClassLoader())
                    );
                })
                .toList();

        return Response.ok(
                Map.of(
                        "claseRecibida", claseRecibida.getName(),
                        "classLoaderRecibido",
                        String.valueOf(claseRecibida.getClassLoader()),
                        "entidadesRegistradas", entidades
                )
        ).build();

    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error en diagnóstico JPA", e);

        return Response.serverError()
                .entity(Map.of("error", e.toString()))
                .build();
    }
}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCompetitor(Competitor competitor) {

        if (competitor == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                            "error", "No se recibieron datos del competidor"
                    ))
                    .build();
        }

        try {
            entityManager.persist(competitor);
            entityManager.flush();

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of(
                            "message", "Competidor registrado correctamente",
                            "id", competitor.getId()
                    ))
                    .build();

        } catch (Exception e) {
            LOGGER.log(
                    Level.SEVERE,
                    "Error al registrar el competidor",
                    e
            );

            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR
            )
                    .entity(Map.of(
                            "error", "No se pudo registrar el competidor"
                    ))
                    .build();
        }
    }
}
