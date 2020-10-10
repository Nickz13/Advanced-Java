package Model;

import java.io.Serializable;

public class StudentInfo extends Student implements Serializable {
    String personalityType;
    String students;

    public StudentInfo(String sid, String score, String personalityType, String students) {
        super(sid, score);
        this.personalityType = personalityType;
        this.students = students;
    }
    public String getpersonalityType() {
        return personalityType;
    }
    public String getStudents() {
        return students;
    }
    
}