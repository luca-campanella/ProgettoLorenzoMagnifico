package it.polimi.ingsw.testingGSON;

/**
 * Created by higla on 17/05/2017.
 */
public class ExamplePerson {
        private String NAME;
        private String LOCATION;
        private String SURNAME;
        private ExamExample EXAM;
        // Getters and setters are not required for this example.
        // GSON sets the fields directly using reflection.

        @Override
        public String toString() {
            return NAME + " - " + LOCATION + " - " + SURNAME + "(" + EXAM + " )";
        }
}
