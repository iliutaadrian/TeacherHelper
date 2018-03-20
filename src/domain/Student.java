package domain;

import java.util.Comparator;

public class Student implements HasId<Integer> {
    private int id;
    private String nume;
    private int grupa;
    private String email;
    private String profesor;

    public Student(int id, String nume, int grupa, String email, String profesor) {
        this.id = id;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
        this.profesor = profesor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public static Comparator<Student> cmpStudent = (Student n1, Student n2)-> n1.getNume().compareTo(n2.getNume());

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", grupa=" + grupa +
                ", email='" + email + '\'' +
                ", profesor='" + profesor + '\'' +
                '}';
    }
}
