package com.example.pracsbc.service;
import org.springframework.stereotype.Service;
import org.apache.jena.query.*;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;

import com.example.pracsbc.entity.Pelicula;
import com.example.pracsbc.entity.Director;

import java.util.ArrayList;
import java.util.List;


@Service
public class Endpoint {

    public void show() {

    }

    public static List<Pelicula> peliculas(String genre) {
        String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n\n" +
                "SELECT ?movie ?title ?director ?genre ?description\n" +
                "WHERE {\n" +
                "    ?movie a dbo:Film ;\n" +
                "        rdfs:label ?title ;\n" +
                "        dbo:director ?director ;\n" +
                "        dbo:abstract ?description ;\n" +
                "        dbo:genre ?genre .\n" +
                "FILTER (REGEX(?genre,\"" + genre + "\",\"i\")).\n" +
                "    FILTER (LANG(?title) = 'en' && LANG(?description) = 'en')\n" +
                "}\n" +
                "ORDER BY ?title";

        // 2. Configurar el endpoint SPARQL
        String sparqlEndpoint = "https://dbpedia.org/sparql";

        // 3. Configurar la consulta SPARQL
        Query query = QueryFactory.create(queryString);


        List<Pelicula> peliculas = new ArrayList<>();
        // 4. Configurar QueryExecutionHTTP con el endpoint SPARQL y la consulta
        try (QueryExecutionHTTP httpQuery = QueryExecutionHTTP.service(sparqlEndpoint, query)) {
            ResultSet results = httpQuery.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String titulo = soln.get("title").toString();
                String genero = soln.get("genre").toString();
                String director = soln.get("director").toString();
                String desc = soln.get("description").toString();

                int i = genero.lastIndexOf('/');
                String g = genero.substring(i + 1);

                if (!peliculas.contains(new Pelicula(titulo, g, director, desc))) {
                    peliculas.add(new Pelicula(titulo, g, director, desc));
                } else {
                    for (Pelicula p : peliculas) {
                        if (p.equals(new Pelicula(titulo, g, director, desc))) {
                            p.addGenre(g);
                            p.addDirector(director);
                        }

                    }
                }


                System.out.println("titulo: " + titulo);
                System.out.println("Genero: " + genero);
                System.out.println();
            }
        }
        return peliculas;
    }

    public static Director informacionDirector(String direc) {
        String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n\n" +
                "SELECT ?director ?fechaNacimiento ?pelicula\n" +
                "WHERE {\n" +
                "    ?director a dbo:Person ;\n" +
                "             dbo:birthDate ?fechaNacimiento .\n" +
                "    ?pelicula dbo:director ?director .\n" +
                "FILTER (REGEX(?director,\"" + direc + "\",\"i\")).\n" +
                "}";

        // 2. Configurar el endpoint SPARQL
        String sparqlEndpoint = "https://dbpedia.org/sparql";

        // 3. Configurar la consulta SPARQL
        Query query = QueryFactory.create(queryString);


        Director dic = null;
        // 4. Configurar QueryExecutionHTTP con el endpoint SPARQL y la consulta
        try (QueryExecutionHTTP httpQuery = QueryExecutionHTTP.service(sparqlEndpoint, query)) {
            ResultSet results = httpQuery.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String director = soln.get("director").toString();
                String fechaNacimiento = soln.get("fechaNacimiento").toString();
                String pelicula = soln.get("pelicula").toString();
                if (dic == null) {
                    dic = new Director(director, fechaNacimiento, pelicula);
                } else {
                    dic.addPelicula(pelicula);
                }

            }

        }

        //segunda consulta
        String queryString2 = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "SELECT ?actor\n" +
                "WHERE {\n" +
                "  ?pelicula dbo:starring ?actor .\n" +
                "  ?pelicula dbo:director ?director .\n" +
                "FILTER (REGEX(?director,\"" + direc + "\",\"i\")).\n" +
                "}\n" +
                "GROUP BY ?actor\n" +
                "HAVING (COUNT(?pelicula) > 1)\n" +
                "ORDER BY DESC(COUNT(?pelicula))";


        // 3. Configurar la consulta SPARQL
        Query query2 = QueryFactory.create(queryString2);

        try (QueryExecutionHTTP httpQuery = QueryExecutionHTTP.service(sparqlEndpoint, query2)) {
            ResultSet results = httpQuery.execSelect();

            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String actor = soln.get("actor").toString();

                dic.addActor(actor);


            }

        }


        return dic;


    }

}