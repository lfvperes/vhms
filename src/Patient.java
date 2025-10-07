public class Patient {
    private String name;
    private byte age;
    private Tutor tutor;
    private Species species;
    private long id;
    private boolean microchip;
    private boolean insurance;
    private List<Appointment> medicalHistory;
    private boolean sex;
    private float weight;
    private String breed;

    public Patient(byte age, String breed, long id, boolean insurance, boolean microchip, String name, boolean sex, Species species, Tutor tutor, float weight) {
        this.age = age;
        this.breed = breed;
        this.id = id;
        this.insurance = insurance;
        this.microchip = microchip;
        this.name = name;
        this.sex = sex;
        this.species = species;
        this.tutor = tutor;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public byte getAge() {
        return age;
    }
    public void setAge(byte age) {
        this.age = age;
    }
    public Tutor getTutor() {
        return tutor;
    }
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
    public Species getSpecies() {
        return species;
    }
    public void setSpecies(Species species) {
        this.species = species;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean isMicrochip() {
        return microchip;
    }
    public void setMicrochip(boolean microchip) {
        this.microchip = microchip;
    }
    public boolean isInsurance() {
        return insurance;
    }
    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }
    public List<Appointment> getMedicalHistory() {
        return medicalHistory;
    }
    public void setMedicalHistory(List<Appointment> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    public boolean isSex() {
        return sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
}
