package com.inventory.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionMapper
        implements ExceptionMapper<Exception> {

    private static final Logger LOG =
            Logger.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {

        // Loga erro real
        LOG.error("Unhandled error", exception);

        // 404
        if (exception instanceof NotFoundException) {

            ErrorResponse error = new ErrorResponse(
                    404,
                    "Not Found",
                    exception.getMessage()
            );

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        // 500
        ErrorResponse error = new ErrorResponse(
                500,
                "Internal Server Error",
                "An unexpected error occurred"
        );

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }
}
