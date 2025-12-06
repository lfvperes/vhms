package com.vhms.model.species;

import com.vhms.model.Patient;
import com.vhms.model.Species;
import com.vhms.model.Tutor;

public class Canine extends Patient {

    public Canine(byte age, String breed, long id, boolean insurance, boolean microchip, String name, boolean sex, Tutor tutor, float weight) {
        // Call the superclass (Patient) constructor, passing Species.Canine
        super(age, breed, id, insurance, microchip, name, sex, Species.Canine, tutor, weight);
    }

    // Canine-specific methods can be added here.
}
