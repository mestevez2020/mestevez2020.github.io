package com.example.pracsbc.service;
import org.springframework.stereotype.Service;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import com.example.pracsbc.entity.pelicula;

import java.util.ArrayList;
import java.util.List;


@Service
public class Endpoint {

    public void  show(){
        // 1. Definir la consulta SPARQL
        String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "SELECT *\n" +
                "WHERE {\n" +
                "  ?person a foaf:Person.\n" +
                "  OPTIONAL { ?person foaf:name ?label }\n" +
                "  OPTIONAL { ?person foaf:homepage ?homepage }\n" +
                "  OPTIONAL { ?person foaf:gender ?gender }\n" +
                "  OPTIONAL { ?person foaf:knows ?friend }\n" +
                "  OPTIONAL { ?person foaf:based_near ?location }\n" +
                "  OPTIONAL { ?person foaf:knows ?friend. ?friend foaf:name ?friendLabel }\n" +
                "  OPTIONAL { ?friend foaf:gender ?friendGender }\n" +
                "  OPTIONAL { ?friend dbo:birthPlace ?birthPlace }\n" +
                "  OPTIONAL { ?birthPlace dbp:country ?friendCountry }\n" +
                "  OPTIONAL { ?friendCountry dbo:populationTotal ?friendCountryPopulation }\n" +
                "}";

        // 2. Configurar el endpoint SPARQL
        String sparqlEndpoint = "https://dbpedia.org/sparql";

        // 3. Configurar la consulta SPARQL
        Query query = QueryFactory.create(queryString);

        // 4. Configurar QueryExecutionHTTP con el endpoint SPARQL y la consulta
        QueryExecutionHTTP httpQuery=QueryExecutionHTTP.service(sparqlEndpoint, query);
        ResultSet results = httpQuery.execSelect();
        try {
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String person = soln.get("person").toString();
                String label = soln.get("label") != null ? soln.get("label").toString() : "";
                String homepage = soln.get("homepage") != null ? soln.get("homepage").toString() : "";
                String gender = soln.get("gender") != null ? soln.get("gender").toString() : "";
                String friend = soln.get("friend") != null ? soln.get("friend").toString() : "";
                String location = soln.get("location") != null ? soln.get("location").toString() : "";
                String friendLabel = soln.get("friendLabel") != null ? soln.get("friendLabel").toString() : "";
                String friendGender = soln.get("friendGender") != null ? soln.get("friendGender").toString() : "";
                String birthPlace = soln.get("birthPlace") != null ? soln.get("birthPlace").toString() : "";
                String friendCountry = soln.get("friendCountry") != null ? soln.get("friendCountry").toString() : "";
                String friendCountryPopulation = soln.get("friendCountryPopulation") != null ? soln.get("friendCountryPopulation").toString() : "";

                System.out.println("Person: " + person);
                System.out.println("Label: " + label);
                System.out.println("Homepage: " + homepage);
                System.out.println("Gender: " + gender);
                System.out.println("Friend: " + friend);
                System.out.println("Location: " + location);
                System.out.println("Friend Label: " + friendLabel);
                System.out.println("Friend Gender: " + friendGender);
                System.out.println("Birth Place: " + birthPlace);
                System.out.println("Friend Country: " + friendCountry);
                System.out.println("Friend Country Population: " + friendCountryPopulation);
                System.out.println();
            }
        } finally {
            // Cerrar el ResultSet manualmente
            results.close();
            // Cerrar QueryExecutionHTTP
            httpQuery.close();
        }

    }

    public static List<pelicula> peliculas(){

        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX yago: <http://dbpedia.org/class/yago/>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "SELECT DISTINCT ?cap ?nombre\n" +
                "WHERE {\n" +
                "  ?cap rdf:type yago:WikicatCapitalsInEurope.\n" +
                "  ?cap foaf:name ?nombre.\n" +
                "}\n" +
                "ORDER BY ?cap";

        // 2. Configurar el endpoint SPARQL
        String sparqlEndpoint = "https://dbpedia.org/sparql";

        // 3. Configurar la consulta SPARQL
        Query query = QueryFactory.create(queryString);


        List<String> capitales = new ArrayList<>();
        List<String> nom_capitales =new ArrayList<>();
        List<pelicula> peliculas = new ArrayList<>();
        // 4. Configurar QueryExecutionHTTP con el endpoint SPARQL y la consulta
        try(QueryExecutionHTTP httpQuery = QueryExecutionHTTP.service(sparqlEndpoint, query)) {
            ResultSet results = httpQuery.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String cap = soln.get("cap").toString();
                String nombre = soln.get("nombre").toString();
                capitales.add(cap);
                nom_capitales.add(nombre);

                peliculas.add(new pelicula(nombre));

                System.out.println("Capital: " + cap);
                System.out.println("Nombre: " + nombre);
                System.out.println();
            }
        }
        return peliculas;
    }
}
