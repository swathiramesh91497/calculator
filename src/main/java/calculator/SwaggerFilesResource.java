/*
 * Copyright 2019 Confluent Inc.
 */

package calculator;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/openapi")
public class SwaggerFilesResource {

    @GET
    @Path("{path:.*}")
    public Response staticResources(@PathParam("path") final String path) throws IOException {

        InputStream resource = null;
        String resourcePath = "/WEB-INF/openapi/" + path;

        File file = new File(resourcePath);
        if (file.getCanonicalPath().startsWith("/WEB-INF/openapi")
                && file.getCanonicalPath().equals(file.getAbsolutePath())) { // prevent directory traversal
            resource = this.getClass().getResourceAsStream(resourcePath);
        }

        return null == resource
                ? Response.status(NOT_FOUND).build()
                : Response.ok().entity(resource).build();
    }

}
