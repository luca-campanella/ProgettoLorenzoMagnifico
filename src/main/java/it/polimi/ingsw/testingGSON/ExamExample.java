package it.polimi.ingsw.testingGSON;

/**
 * Created by higla on 17/05/2017.
 */
public class ExamExample {
        private String SUBJECT;
        private double GRADE;

        @Override
        public String toString() {
            return SUBJECT + " - " + GRADE;
        }
}