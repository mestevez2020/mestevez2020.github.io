package com.example.pracsbc.service;
import org.springframework.stereotype.Service;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;

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
}
