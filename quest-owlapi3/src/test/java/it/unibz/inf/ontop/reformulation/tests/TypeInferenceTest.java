package it.unibz.inf.ontop.reformulation.tests;

import com.google.common.collect.ImmutableList;
import it.unibz.inf.ontop.injection.QuestConfiguration;
import it.unibz.inf.ontop.model.OBDADataFactory;
import it.unibz.inf.ontop.model.OBDAModel;
import it.unibz.inf.ontop.model.impl.OBDADataFactoryImpl;
import it.unibz.inf.ontop.owlrefplatform.owlapi.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * test Type inference in the SQL Generator with the use of metadata when the value is unknown.
 * @see it.unibz.inf.ontop.owlrefplatform.core.srcquerygeneration.TypeExtractor
 */
public class TypeInferenceTest {

    private Connection conn;

    private static final String ONTOLOGY_FILE = "src/test/resources/test/typeinference/types.owl";
    private static final String OBDA_FILE = "src/test/resources/test/typeinference/types.obda";
    private static final String CREATE_DB_FILE = "src/test/resources/test/typeinference/types-create-db.sql";
    private static final String DROP_DB_FILE = "src/test/resources/test/typeinference/types-drop-db.sql";

    @Before
    public void setUp() throws Exception {

        String url = "jdbc:h2:mem:types";
        String username = "sa";
        String password = "";

        conn = DriverManager.getConnection(url, username, password);


        Statement st = conn.createStatement();

        FileReader reader = new FileReader(CREATE_DB_FILE);
        BufferedReader in = new BufferedReader(reader);
        StringBuilder bf = new StringBuilder();
        String line = in.readLine();
        while (line != null) {
            bf.append(line);
            line = in.readLine();
        }
        in.close();

        st.executeUpdate(bf.toString());
        conn.commit();
    }

    @After
    public void tearDown() throws Exception {

        dropTables();
        conn.close();

    }

    private void dropTables() throws SQLException, IOException {

        Statement st = conn.createStatement();

        FileReader reader = new FileReader(DROP_DB_FILE);
        BufferedReader in = new BufferedReader(reader);
        StringBuilder bf = new StringBuilder();
        String line = in.readLine();
        while (line != null) {
            bf.append(line);
            line = in.readLine();
        }
        in.close();

        st.executeUpdate(bf.toString());
        st.close();
        conn.commit();
    }

    @Test
    public void testType() throws Exception {
        String queryBind = "PREFIX : <http://example.org/types/voc#>\n" +
                "\n" +
                "SELECT ?r \n" +
                "WHERE {\n" +
                "?x a :Asian_Company ; :hasCompanyLocation ?r .  "+
                "}";

        ImmutableList<String> expectedValues = ImmutableList.of(
                "<http://example.org/types/voc#Philippines>",
                "<http://example.org/types/voc#China>"

        );
        checkReturnedValues(queryBind, expectedValues);
    }

    private void checkReturnedValues(String query, List<String> expectedValues) throws Exception {

        QuestOWLFactory factory = new QuestOWLFactory();
        QuestConfiguration config = QuestConfiguration.defaultBuilder()
                .nativeOntopMappingFile(OBDA_FILE)
                .ontologyFile(ONTOLOGY_FILE)
                .build();
        QuestOWL reasoner = factory.createReasoner(config);

        // Now we are ready for querying
        QuestOWLConnection conn = reasoner.getConnection();
        QuestOWLStatement st = conn.createStatement();

        int i = 0;
        List<String> returnedValues = new ArrayList<>();
        try {
            QuestOWLResultSet rs = st.executeTuple(query);
            while (rs.nextRow()) {
                OWLObject ind1 = rs.getOWLObject("r");
                // log.debug(ind1.toString());
                returnedValues.add(ind1.toString());
                System.out.println(ind1);
                i++;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
            reasoner.dispose();
        }
        assertTrue(String.format("%s instead of \n %s", returnedValues.toString(), expectedValues.toString()),
                returnedValues.equals(expectedValues));
        assertTrue(String.format("Wrong size: %d (expected %d)", i, expectedValues.size()), expectedValues.size() == i);

    }
}
