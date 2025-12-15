package com.vhms.model.species;

import com.vhms.model.Patient;
import com.vhms.model.Species;
import com.vhms.model.Tutor;

public class Fish extends Patient {

    // Updated constructor: removed 'long id'
    public Fish(byte age, String breed, boolean insurance, boolean microchip, String name, boolean sex, Tutor tutor, float weight) {
        super(age, breed, insurance, microchip, name, sex, Species.Fish, tutor, weight);
    }
}

