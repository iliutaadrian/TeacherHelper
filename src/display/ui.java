package display;

import domain.Laboratoare;
import domain.Nota;
import domain.Student;
import service.LaboratorService;
import service.NotaService;
import service.StudentService;
import validator.*;

import java.util.Scanner;

public class ui {
    NotaService ctrlN;
    StudentService ctrlS;
    LaboratorService ctrlL;

    Scanner scanner = new Scanner(System.in);

    public ui(NotaService ctrlN, StudentService ctrlS, LaboratorService ctrlL) {

        this.ctrlN = ctrlN;
        this.ctrlS = ctrlS;
        this.ctrlL = ctrlL;
    }

    private void show_start_menu(){
        System.out.println("-----------MENU------------");
        System.out.println("1. Adauga o nota.");
        System.out.println("2. Afisare toate notele.");
        System.out.println("3. Modifica o nota.");
        System.out.println("4. Adauga student");
        System.out.println("5. Afisare studenti");
        System.out.println("6. Sterge student");
        System.out.println("7. Adauga laborator");
        System.out.println("8. Afisare laborator");
        System.out.println("9. Sterge laborator");
        System.out.println("10.Filtrari");
        System.out.println("11.Undo");
        System.out.println("0.Exit.");
        System.out.print("Optiunea dumneavoastra:");
    }

    private void show_filtrari_menu(){
        System.out.println("1.Filtrari note(nota)");
        System.out.println("2.Filtrari studenti(nume)");
        System.out.println("3.Filtrari laboratoare(id)");
        System.out.println("0.Revenire meniu principal.");
    }

    public void start_menu(){
        while(true) {
            show_start_menu();
            int optiune = scanner.nextInt();

            switch (optiune){
                case 1:
                    adauga_menu();
                    break;
                case 2:
                    afisare_menu();
                    break;
                case 3:
                    modifica_menu();
                    break;
                case 4:
                    adauga_student();
                    break;
                case 5:
                    afisare_student();
                    break;
                case 6:
                    sterge_student();
                    break;
                case 7:
                    adauga_laborator();
                    break;
                case 8:
                    afisare_laborator();
                    break;
                case 9:
                    sterge_laborator();
                    break;
                case 10:
                    filtrari_menu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Optiunea gresita.");
            }

        }
    }

    public void filtrari_menu(){
        show_filtrari_menu();
        System.out.print("Optiune: ");
        int optiune = scanner.nextInt();

        switch (optiune) {
            case 1:
                filtrari_note();
                break;
            case 2:
                filtrari_studenti();
                break;
            case 3:
                filtrari_laboratoare();
                break;
            case 0:
                return;
            default:
                System.out.println("Optiunea gresita.");
        }
    }

    private void filtrari_laboratoare() {
        System.out.print("Valoarea minima:");
        int min = scanner.nextInt();
        System.out.print("Valoarea maxima");
        int max = scanner.nextInt();
        ctrlL.filter(min,max).forEach(System.out::println);
    }

    private void filtrari_studenti() {
        System.out.print("Primul caracter: ");
        String s ;
        Scanner scanner1 = new Scanner(System.in);

        s= scanner1.nextLine();

        ctrlS.filterNume(s).forEach(System.out::println);
    }

    private void filtrari_note() {
        System.out.print("Valoarea minima:");
        int min = scanner.nextInt();
        System.out.print("Valoarea maxima");
        int max = scanner.nextInt();

        System.out.println("Crescator dupa note:");
        ctrlN.filter(min,max).forEach(System.out::println);
        System.out.println("Descrescator");
        ctrlN.filter_desc(min,max).forEach(System.out::println);
    }

    private void sterge_laborator() {
        afisare_laborator();
        int id;

        try {
            id = scanner.nextInt();
            ctrlL.remove(id);
            ctrlN.sterge_laborator(id);
            System.out.println("Sters cu succes!");
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    private void afisare_laborator() {
        for(Laboratoare l : ctrlL.find_all())
            System.out.println(l);
    }

    private void adauga_laborator() {
        int id;
        String descriere;
        int deadline;
        int nota;

        try{
            System.out.print("Id laborator: ");
            id = scanner.nextInt();

            System.out.print("Descriere laborator: ");
            descriere = scanner.nextLine();

            System.out.print("Deadline laborator: ");
            deadline = scanner.nextInt();

            ctrlL.adauga(id, descriere, deadline);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }

    }

    private void afisare_student() {
        for(Student s :ctrlS.find_all())
            System.out.println(s);
    }

    private void sterge_student() {
        afisare_student();
        int id;


        try {
            id = scanner.nextInt();
            ctrlS.remove(id);
            ctrlN.sterge_student(id);
            System.out.println("Sters cu succes!");
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    private void adauga_student() {
        int id;
        String nume;
        int grupa;
        String email;
        String profesor;

        try{
            System.out.print("Id student: ");
            id = scanner.nextInt();

            System.out.print("Nume student: ");
            nume = scanner.nextLine();

            System.out.print("Grupa student: ");
            grupa = scanner.nextInt();

            System.out.print("Email student: ");
            email= scanner.nextLine();

            System.out.print("Profesor student: ");
            profesor = scanner.nextLine();

            ctrlS.adauga(id, nume, grupa, email, profesor);

            System.out.println("Adaugat cu succes!");
        } catch (ValidatorException e) {
            System.out.print(e.getMessage());
        }

    }

    private void modifica_menu() {
        int idNota;
        int nota;
        int predare;

        afisare_menu();

        try {
        System.out.print("Id-ul notei: ");
        idNota = scanner.nextInt();

        System.out.print("Saptamana in care a fost predat: ");
        predare = scanner.nextInt();

        System.out.print("Nota acordata: ");
        nota = scanner.nextInt();

        ctrlN.modifica(idNota,nota,predare);

        System.out.println("Modificat cu succes!");
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }

    }

    private void afisare_menu() {
        for (Nota n: ctrlN.find_all())
            System.out.println(n);
    }

    private void adauga_menu() {
        int idNota;
        int idStudent;
        int idLaborator;
        int predare;
        int valoare;

        try {
            System.out.print("Id-ul notei: ");
            idNota = scanner.nextInt();

            System.out.print("Id-ul studentului: ");
            idStudent = scanner.nextInt();

            System.out.print("Id-ul laboratorului: ");
            idLaborator = scanner.nextInt();

            System.out.print("Saptamana in care a fost predat: ");
            predare = scanner.nextInt();

            System.out.print("Nota acordata: ");
            valoare = scanner.nextInt();

            ctrlN.adauga(idNota,idStudent,idLaborator,predare,valoare);
            System.out.println("Adaugat cu succes!");
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }

    }
}
