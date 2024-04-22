package com.example.pracsbc.service;
import com.example.pracsbc.entity.Actor;
import com.example.pracsbc.entity.Ciudad;
import org.apache.jena.rdf.model.RDFNode;
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
            }
        }
        return peliculas;
    }
    public static List<Pelicula> peliculasBytitle(String title) {
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
                "FILTER (REGEX(?title,\"" + title + "\",\"i\")).\n" +
                "    FILTER (LANG(?title) = 'en' && LANG(?description) = 'en')\n" +
                "}\n" +
                "ORDER BY ?title";

        System.out.println(queryString);
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
            }
        }
        return peliculas;
    }

    public static Director informacionDirector(String direc) {
        String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n\n" +
                "SELECT ?director ?fechaNacimiento ?pelicula ?title\n" +
                "WHERE {\n" +
                "    ?director a dbo:Person .\n" +
                "    OPTIONAL{ ?director dbo:birthDate ?fechaNacimiento }.\n" +
                "    ?pelicula dbo:director ?director ;\n" +
                "              a dbo:Film ;\n" +
                "              rdfs:label ?title .\n" +
                "    FILTER (REGEX(?director, \"" + direc + "\", \"i\")) .\n" +
                "    FILTER (LANG(?title) = 'en')\n" +
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
                String director = soln.get("director").toString().substring(soln.get("director").toString().lastIndexOf('/')+1);;
                String pelicula = soln.get("title").toString().substring(1,soln.get("title").toString().lastIndexOf('\"'));
                RDFNode fechaNacimientoNode = soln.get("fechaNacimiento");

                String fechaNacimiento = (fechaNacimientoNode != null) ? fechaNacimientoNode.toString() : "Fecha de nacimiento desconocida";


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


    public static Actor informacionActor(String actor) {
        String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n\n" +
                "SELECT ?actor ?fechaNaci ?paisNaci ?pelicula ?title\n" +
                "WHERE {\n" +
                "    ?actor a dbo:Person ;\n" +
                "             dbo:birthDate ?fechaNaci ;\n" +
                "             dbo:birthPlace ?paisNaci .\n" + // Añadido espacio y punto final
                "    ?pelicula dbo:starring ?actor ;\n" +
                "              a dbo:Film ;\n" +
                "              rdfs:label ?title .\n" +
                "FILTER (REGEX(?actor,\"" + actor + "\",\"i\")).\n" +
                "}";

        // 2. Configurar el endpoint SPARQL
        String sparqlEndpoint = "https://dbpedia.org/sparql";

        // 3. Configurar la consulta SPARQL
        Query query = QueryFactory.create(queryString);


        Actor ac = null;
        // 4. Configurar QueryExecutionHTTP con el endpoint SPARQL y la consulta
        try (QueryExecutionHTTP httpQuery = QueryExecutionHTTP.service(sparqlEndpoint, query)) {
            ResultSet results = httpQuery.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String a = soln.get("actor").toString().substring(soln.get("actor").toString().lastIndexOf('/')+1);;
                String fechaNaci = soln.get("fechaNaci").toString().substring(1,11);
                String paisNaci = soln.get("paisNaci").toString().substring(soln.get("paisNaci").toString().lastIndexOf('/')+1);;
                String pelicula = soln.get("title").toString().substring(1,soln.get("title").toString().lastIndexOf('\"'));
                if (ac == null) {
                    ac = new Actor(a, fechaNaci, paisNaci);
                } else {
                    ac.addPeliculas(new Pelicula(pelicula));
                }
            }
        }
        return ac;
    }
    public static Ciudad Ciudad(String ciu) {
        String queryString = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n\n" +
                "SELECT ?pais ?ciudad ?extension\n" +
                "WHERE {\n" +
                "    ?ciudad a dbo:City. \n" +
                "OPTIONAL{    ?ciudad dbo:areaTotal ?extension} . \n" +
                "OPTIONAL{    ?ciudad dbo:country ?pais }.\n"  +
                "FILTER (REGEX(?ciudad,\"" + ciu + "\",\"i\")).\n"+
                "}\n" +
                "ORDER BY ?ciudad";
        // 2. Configurar el endpoint SPARQL
        String sparqlEndpoint = "https://dbpedia.org/sparql";

        // 3. Configurar la consulta SPARQL
        Query query = QueryFactory.create(queryString);
        // 4. Configurar QueryExecutionHTTP con el endpoint SPARQL y la consulta
        Ciudad ci=null;
        try (QueryExecutionHTTP httpQuery = QueryExecutionHTTP.service(sparqlEndpoint, query)) {
            ResultSet results = httpQuery.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String nombre = soln.get("ciudad").toString().substring(soln.get("ciudad").toString().lastIndexOf('/')+1);

                RDFNode paisNode = soln.get("pais");

                String pais = (paisNode != null) ? paisNode.toString().substring(soln.get("pais").toString().lastIndexOf('/')+1) : "Pais desconocido";

                RDFNode extensionNode = soln.get("extension");

                String extension = (extensionNode != null) ? extensionNode.toString().substring(1,soln.get("extension").toString().lastIndexOf('\"')): "Extension desconocida";
                ci=new Ciudad(nombre, extension, pais);
            }

        }
        return ci;
    }

}





