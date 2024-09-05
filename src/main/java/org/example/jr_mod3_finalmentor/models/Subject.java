package org.example.jr_mod3_finalmentor.models;

public enum Subject {
    ALGEBRA("Алгебра"),
    BIOLOGY("Биология"),
    DRAWING("Изобразительное искусство"),
    CHEMISTRY("Химия"),
    COMPUTER_SCIENCE("Информатика"),
    GEOGRAPHY("География"),
    GEOMETRY("Геометрия"),
    HISTORY("История"),
    LITERATURE("Литература"),
    MUSIC("Музыка"),
    PHYSICAL_EDUCATION("Физическая культура"),
    PHYSICS("Физика"),
    TECHNOLOGY("Труды");

    public final String label;

    Subject(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
