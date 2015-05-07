package at.tugraz.beislrallye;

/**
 * Created by Matthias on 02.05.2015.
 */
public class AutocompleteData {
    private final String description;
    private final String placeId;

    public AutocompleteData(String description, String place_id) {
        this.description = description;
        this.placeId = place_id;
    }

    public String getDescription() {
        return description;
    }
}
