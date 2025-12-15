package com.vhms.model.species;

import com.vhms.model.Patient;
import com.vhms.model.Species;
import com.vhms.model.Tutor;

public class Canine extends Patient {

    // Updated constructor: removed 'long id'
    public Canine(byte age, String breed, boolean insurance, boolean microchip, String name, boolean sex, Tutor tutor, float weight) {
        // Call the superclass (Patient) constructor, passing Species.Canine
        super(age, breed, insurance, microchip, name, sex, Species.Canine, tutor, weight);
    }

    // Canine-specific methods can be added here.
}

