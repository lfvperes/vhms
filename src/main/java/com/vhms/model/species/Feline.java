package com.vhms.model.species;

import com.vhms.model.Patient;
import com.vhms.model.Species;
import com.vhms.model.Tutor;

public class Feline extends Patient {

    // Updated constructor: removed 'long id'
    public Feline(byte age, String breed, boolean insurance, boolean microchip, String name, boolean sex, Tutor tutor, float weight) {
        // Call the superclass (Patient) constructor, passing Species.Feline as the species
        super(age, breed, insurance, microchip, name, sex, Species.Feline, tutor, weight);
    }
}

