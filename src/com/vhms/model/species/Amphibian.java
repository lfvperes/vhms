package com.vhms.model.species;

import com.vhms.model.Patient;
import com.vhms.model.Species;
import com.vhms.model.Tutor;

public class Amphibian extends Patient {

    public Amphibian(byte age, String breed, long id, boolean insurance, boolean microchip, String name, boolean sex, Tutor tutor, float weight) {
        super(age, breed, id, insurance, microchip, name, sex, Species.Amphibian, tutor, weight);
    }
}
