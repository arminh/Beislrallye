package at.tugraz.beislrallye;

import android.util.Log;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by armin on 07.05.15.
 */
public class PlaceGeneratorTest extends TestCase {
    private String jsonTest = "[{\"geometry\" : {\"location\" : {\"lat\" : 47.070349,\"lng\" : 15.434511}},\"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/bar-71.png\",\"id\" : \"8806af9e69e3e29d7b7c84e3fd5a7eaad6c1dc00\",\"name\" : \"Hotel Wiesler\",\"photos\" : [{\"height\" : 6144,\"html_attributions\" : [ \"Von einem Google-Nutzer\" ],\"photo_reference\" : \"CoQBdwAAALUvaVhmUFU8jHUEYNGwvfcZ_wBQf-KGIjDxP1HrE1v9pJTGuO3YrR5uTqisRWHoWE3YDtMzNgZw8iBWeFFSMSzxolDkE8y2lu7zX8Nm2i5lO2-yfa02e-9fnn-XG130c13Fdq1tEu361m_-Ws4j0np_YIKToQ8BiVt6MvwM0EHwEhAiCZ5qmTt-5rjC0bYDz59qGhTbAsQOnu6b7AFeQx8O0_8tPiNqUw\",\"width\" : 4096}],\"place_id\" : \"ChIJ5dcRRXg1bkcRDJbbLnHSlII\",\"rating\" : 4.1,\"reference\" : \"CnRhAAAAU-hJpHApYJNWfESuIXXjhMETIS-7Hf1dFm-pgvHl2mnrj9Slhrhe823Wk_RA66uBhTgxjFWgPhy6MwM54BczEGTgGkH5ONR5dhqxO9mwtjqpIykcyurgRv2R9R4cDdlJnyjNQrmP26IxrtT0ZL-zXhIQ_wlmEjRKj8ifxRAW6UhNDxoUrX1HACm306K_5xwrOfoHD8YbD4k\",\"scope\" : \"GOOGLE\",\"types\" : [ \"bar\", \"restaurant\", \"lodging\", \"food\", \"establishment\" ],\"vicinity\" : \"Grieskai 4-8, Graz\"}]";

    public void testGeneratePlaces() {
        ArrayList<Place> results = PlacesGenerator.generatePlaces(jsonTest);

        assertEquals(1, results.size());

        for (Place result : results) {
            assertEquals("Hotel Wiesler", result.getName());
            assertEquals(47.070349, result.getLat());
            assertEquals(15.434511, result.getLng());
            assertEquals("Grieskai 4-8, Graz", result.getAddress());
            assertEquals("8806af9e69e3e29d7b7c84e3fd5a7eaad6c1dc00", result.getId());
            assertEquals("CoQBdwAAALUvaVhmUFU8jHUEYNGwvfcZ_wBQf-KGIjDxP1HrE1v9pJTGuO3YrR5uTqisRWHoWE3YDtMzNgZw8iBWeFFSMSzxolDkE8y2lu7zX8Nm2i5lO2-yfa02e-9fnn-XG130c13Fdq1tEu361m_-Ws4j0np_YIKToQ8BiVt6MvwM0EHwEhAiCZ5qmTt-5rjC0bYDz59qGhTbAsQOnu6b7AFeQx8O0_8tPiNqUw", result.getPhotoId());
        }
    }
}
