package com.example.pracsbc.service;
import org.springframework.stereotype.Service;
import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import com.example.pracsbc.entity.Pelicula;

import java.util.ArrayList;
import java.util.List;


@Service
public class Endpoint {

    public void  show(){

    }

    public static List<Pelicula> peliculas(){

        String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n\n" +
                "SELECT ?movie ?title ?genre\n" +
                "WHERE {\n" +
                "    ?movie a dbo:Film ;\n" +
                "        rdfs:label ?title ;\n" +
                "        dbo:genre ?genre .\n" +
                "    FILTER (LANG(?title) = 'en')\n" +
                "}\n" +
                "ORDER BY ?title";

        // 2. Configurar el endpoint SPARQL
        String sparqlEndpoint = "https://dbpedia.org/sparql";

        // 3. Configurar la consulta SPARQL
        Query query = QueryFactory.create(queryString);


        List<Pelicula> peliculas = new ArrayList<>();
        // 4. Configurar QueryExecutionHTTP con el endpoint SPARQL y la consulta
        try(QueryExecutionHTTP httpQuery = QueryExecutionHTTP.service(sparqlEndpoint, query)) {
            ResultSet results = httpQuery.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String cap = soln.get("movie").toString();
                String titulo = soln.get("title").toString();
                String genero = soln.get("genre").toString();


                peliculas.add(new Pelicula(titulo,genero));

                System.out.println("titulo: " + titulo);
                System.out.println("Genero: " + genero);
                System.out.println();
            }
        }
        return peliculas;
    }
}