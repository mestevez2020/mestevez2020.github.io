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

    public static List<Pelicula> peliculas(String genre){
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
                "FILTER (REGEX(?genre,\""+genre+"\",\"i\")).\n"+
                "    FILTER (LANG(?title) = 'en' && LANG(?description) = 'en')\n" +
                "}\n" +
                "ORDER BY ?title"
                ;

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
                String titulo = soln.get("title").toString();
                String genero = soln.get("genre").toString();
                String director = soln.get("director").toString();
                String desc = soln.get("description").toString();

                int i=genero.lastIndexOf('/');
                String g=genero.substring(i+1);

                if(!peliculas.contains(new Pelicula(titulo,g,director,desc))){
                    peliculas.add(new Pelicula(titulo,g,director,desc));
                }
                else{
                    for(Pelicula p:peliculas){
                        if(p.equals(new Pelicula(titulo,g,director,desc))){
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

}