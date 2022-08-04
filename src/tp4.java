import java.sql.*;
import java.util.Scanner;

public class tp4 {
    static final String DB_URL = "jdbc:mysql://localhost/tp master camps";
    static final String USER = "root";
    static final String PASS = "Pachat93";
    static final String documentQuery = "select * from Document";
    static final String categoryQuery = "select * from Document WHERE Category = ";
    static final String sujetQuery = " select * from Document where topic_id = ";
    static final String tagQuery = " select d.* from Associate a, Document d WHERE a.document_id = d.document_id AND tag_id = \"";
    static final String topicQuery = "select t.* from Document d, topic t where d.topic_id = t.topic_id GROUP BY d.topic_id Order by COUNT(d.topic_id) LIMIT 1";
    static final String occurenceTag = "SELECT tag, COUNT(*) occurrences FROM tag, associate WHERE tag = tag_id GROUP BY tag";
    static final String newdocumentQuery = "INSERT INTO document(document_Id, document_name, storageadresse, category, topic_id) VALUES (";
    static final String datedocuementAjouteStart = "UPDATE Document SET document_date = ";
    static final String datedocuementAjouteEnd = " WHERE document_id = ";
    static final String newtagDoc = "INSERT INTO associate (document_id, tag_id) VALUES (";
    public static final Scanner scanner = new Scanner(System.in);
    private static final int CHOICE_AMOUNT = 10;
    private static final int MENU_WIDTH = 44;

    public static void main(String[] args) throws ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            try {
                int menuChoice;
                while ((menuChoice = menuChoiceScanner()) != -1) {
                    switch (menuChoice) {
                        case 1:
                            findDocumentsBy(conn, "Catégorie ID des documents : ", categoryQuery);
                            break;
                        case 2:
                            findDocumentsBy(conn, "Sujet ID des documents : ", sujetQuery);
                            break;
                        case 3:
                            findDocumentsByTag(conn, "Tag des documents : ", tagQuery);
                            break;
                        case 4:
                            findTopics(conn, topicQuery);
                            break;
                        case 5:
                            findTags(conn, occurenceTag);
                            break;
                        case 6:
                            addDocument(conn, newdocumentQuery);
                            break;
                        case 7:
                            addDateToDocument(conn);
                            break;
                        case 8:
                            addTagToDocument(conn);
                            break;
                        case 9:
                            findDocuments(conn, documentQuery);
                            break;
                        default:
                            return;
                    }
                }
            } catch (ExitException ignored) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void findDocumentsBy(Connection conn, String condition, String query) {
        int id = scanNumber(condition);
        String finalQuery = query.concat(String.valueOf(id));
        findDocuments(conn, finalQuery);
    }

    private static void findDocumentsByTag(Connection conn, String condition, String query) {
        System.out.print(condition);
        String word = scanner.nextLine();
        String finalQuery = query.concat(word.concat("\""));
        findDocuments(conn, finalQuery);
    }

    private static void addDocument(Connection conn, String query) {
        int documentId = scanNumber("ID du nouveau document : ");
        System.out.print("Nom du nouveau document : ");
        String documentName = scanner.nextLine();
        System.out.print("Adresse du nouveau document : ");
        String storageAddress = scanner.nextLine();
        int categoryId = scanNumber("Category ID du nouveau document : ");
        int topicId = scanNumber("Topic ID du nouveau document : ");
        String finalQuery = query.concat(
                String.valueOf(documentId).concat(",")
                        .concat("\"" + documentName).concat("\",")
                        .concat("\"" + storageAddress).concat("\",")
                        .concat(String.valueOf(categoryId)).concat(",")
                        .concat(String.valueOf(topicId)).concat(")"));
        try {
            conn.createStatement().executeUpdate(finalQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addDateToDocument(Connection conn){
        int documentId = scanNumber("ID du document à modifier : ");
        String documentDate = scanDate("Date du nouveau document (YYYY-MM-DD) : ");
        String finalQuery = datedocuementAjouteStart.concat("\"" + documentDate + "\"").concat(datedocuementAjouteEnd).concat(String.valueOf(documentId));
        try {
            conn.createStatement().executeUpdate(finalQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addTagToDocument(Connection conn){
        int documentId = scanNumber("ID du document à modifier : ");
        System.out.print("Tag à associé au document : ");
        String tag = scanner.nextLine();
        String finalQuery = newtagDoc.concat(String.valueOf(documentId)).concat(", ").concat("\"" + tag + "\")");
        try {
            conn.createStatement().executeUpdate(finalQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void findDocuments(Connection conn, String query) {
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()) {
                System.out.print("ID: " + rs.getInt("document_id"));
                System.out.print(", Name: " + rs.getString("document_name"));
                System.out.print(", Adresse: " + rs.getString("storageadresse"));
                System.out.print(", Date: " + rs.getDate("document_date"));
                System.out.print(", Category id: " + rs.getInt("Category"));
                System.out.println(", Topic id: " + rs.getInt("topic_Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void findTopics(Connection conn, String query) {
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()) {
                System.out.print("ID: " + rs.getInt("topic_id"));
                System.out.println(", Sujet: " + rs.getString("topic"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void findTags(Connection conn, String query) {
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            while (rs.next()) {
                System.out.print("Tag : " + rs.getString("tag"));
                System.out.println(", Occurrences : " + rs.getInt("occurrences"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int scanNumber(String condition) {
        System.out.print(condition);
        String id = scanner.nextLine();
        while (!id.matches("\\d+")) {
            System.out.print("Saisie invalide !\n" + condition);
            id = scanner.nextLine();
        }
        return Integer.parseInt(id);
    }

    private static String scanDate(String condition) {
        System.out.print(condition);
        String date = scanner.nextLine();
        while (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.print("Saisie invalide !\n" + condition);
            date = scanner.nextLine();
        }
        return date;
    }
    private static int menuChoiceScanner() throws ExitException {
        displayMenu();
        String menuChoice = scanner.nextLine();
        int nbChoice;
        while (!menuChoice.matches("\\d+") || (nbChoice = Integer.parseInt(menuChoice)) <= 0 || nbChoice > CHOICE_AMOUNT) {
            System.out.println("Invalid choice from the menu. Please choose another option :\n");
            displayMenu();
            menuChoice = scanner.nextLine();
        }
        if (nbChoice == CHOICE_AMOUNT) throw new ExitException();
        return nbChoice;
    }

    private static void displayMenu() {
        System.out.print(new String(new char[MENU_WIDTH]).replace("\0", "*"));
        System.out.println("\n\t\tWelcome to the Mastercamp's TP4 !\n");
        System.out.println("1) Trouver documents par catégorie");
        System.out.println("2) Trouver documents par sujet");
        System.out.println("3) Trouver documents par tag");
        System.out.println("4) Récupérer le sujet le plus fréquent");
        System.out.println("5) Afficher le nombre d'occurence par tag");
        System.out.println("6) Ajouter un nouveau document");
        System.out.println("7) Ajouter une date à un document");
        System.out.println("8) Ajouter un tag à un document");
        System.out.println("9) Afficher tous les documents");
        System.out.println("10) Quit\n");
        System.out.print(new String(new char[MENU_WIDTH]).replace("\0", "*").concat("\n"));
    }
}
