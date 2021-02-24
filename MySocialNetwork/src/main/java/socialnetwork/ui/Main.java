package socialnetwork.ui;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.*;
import socialnetwork.domain.exceptions.ValidationException;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.*;
import socialnetwork.service.FriendRequestService;
import socialnetwork.service.MessageService;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.UtilizatorService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        try {
            String fileNameUtilizatori = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
            String fileNamePrietenii = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.prietenii");
            String fileNameMesaje = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.mesaje");
            String fileNameRecipients = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.recipients");
            String fileNameFriendRequests = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.friendrequests");

            Repository<Long, Utilizator> userRepository;
            Repository<Tuple<Long, Long>, Prietenie> userRepositoryPrietenie;
            UtilizatorService userService;
            PrietenieService userServicePrietenie;

            MessageFile messageRepository;
            RecipientFile recipientRepository;
            MessageService messageService;

            FriendRequestFile friendRequestRepository;
            FriendRequestService friendRequestService;

            while (true) {
                System.out.println();
                System.out.println("My Social Network:");
                System.out.println();
                System.out.println("1. Adauga un utilizator");
                System.out.println("2. Sterge un utilizator");
                System.out.println("3. Adauga un prieten");
                System.out.println("4. Sterge un prieten");
                System.out.println("5. Afiseaza numarul de comunitati");
                System.out.println("6. Afiseaza cea mai sociabila comunitate");
                System.out.println("7. Afiseaza toate relatiile de prietenie ale unui utilizator");
                System.out.println("8. Afiseaza toate realtiile de prietenie ale unui utilizator dintr-o luna data");
                System.out.println("9. Afiseaza conversatiile a doi utilizatori");
                System.out.println("10. Trimite mesaje");
                System.out.println("11. Raspunde la mesaje");
                System.out.println("12. Trimite o cerere de prietenie");
                System.out.println("13. Accepta sau refuza cereri de prietenie primite");
                System.out.println("14. Iesi din aplicatie");
                System.out.println();

                userRepository = new UtilizatorFile(fileNameUtilizatori, new UtilizatorValidator());
                userRepositoryPrietenie = new PrietenieFile(fileNamePrietenii, new PrietenieValidator());

                userService = new UtilizatorService(userRepository, userRepositoryPrietenie);
                userServicePrietenie = new PrietenieService(userRepository, userRepositoryPrietenie);

                messageRepository = new MessageFile(fileNameMesaje, new MessageValidator());
                recipientRepository = new RecipientFile(fileNameRecipients, new RecipientValidator());

                messageService = new MessageService(userRepository, messageRepository, recipientRepository);

                friendRequestRepository = new FriendRequestFile(fileNameFriendRequests, new FriendRequestValidator());
                friendRequestService = new FriendRequestService(friendRequestRepository, userRepository, userServicePrietenie);

                Scanner scanner = new Scanner(System.in);
                System.out.print("Introduceti numarul comenzii: ");
                int c = scanner.nextInt();
                scanner.nextLine();

                if (c == 1) {
                    System.out.print("Introduceti numele utilizatorului: ");
                    String nume = scanner.nextLine();
                    System.out.print("Introduceti prenumele utilizatorului: ");
                    String prenume = scanner.nextLine();
                    Utilizator utilizator = new Utilizator(nume, prenume);

                    try {
                        userService.addUtilizator(utilizator);
                        System.out.println();
                        System.out.println("Utilizator adaugat cu succes!");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 2) {
                    System.out.print("Introduceti numele utilizatorului: ");
                    String nume = scanner.nextLine();
                    System.out.print("Introduceti prenumele utilizatorului: ");
                    String prenume = scanner.nextLine();
                    Utilizator utilizator = new Utilizator(nume, prenume);

                    try {
                        userService.deleteUtilizator(utilizator);
                        System.out.println();
                        System.out.println("Utilizator sters cu succes!");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 3) {
                    System.out.print("Introduceti numele primului utilizator: ");
                    String nume1 = scanner.nextLine();
                    System.out.print("Introduceti prenumele primului utilizatorului: ");
                    String prenume1 = scanner.nextLine();
                    Utilizator utilizator1 = new Utilizator(nume1, prenume1);

                    System.out.print("Introduceti numele celui de-al doilea utilizator: ");
                    String nume2 = scanner.nextLine();
                    System.out.print("Introduceti prenumele celui de-al doilea utilizator: ");
                    String prenume2 = scanner.nextLine();
                    Utilizator utilizator2 = new Utilizator(nume2, prenume2);

                    System.out.print("Introduceti data formarii prieteniei (yyyy-MM-dd HH:mm): ");
                    String date = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                    try {
                        userServicePrietenie.addPrietenie(utilizator1, utilizator2, dateTime);
                        System.out.println();
                        System.out.println("Prietenie adaugata cu succes!");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 4) {
                    System.out.print("Introduceti numele primului utilizator: ");
                    String nume1 = scanner.nextLine();
                    System.out.print("Introduceti prenumele primului utilizatorului: ");
                    String prenume1 = scanner.nextLine();
                    Utilizator utilizator1 = new Utilizator(nume1, prenume1);

                    System.out.print("Introduceti numele celui de-al doilea utilizator: ");
                    String nume2 = scanner.nextLine();
                    System.out.print("Introduceti prenumele celui de-al doilea utilizator: ");
                    String prenume2 = scanner.nextLine();
                    Utilizator utilizator2 = new Utilizator(nume2, prenume2);


                    System.out.print("Introduceti data formarii prieteniei (yyyy-MM-dd HH:mm): ");
                    String date = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                    try {
                        userServicePrietenie.deletePrietenie(utilizator1, utilizator2, dateTime);
                        System.out.println();
                        System.out.println("Prietenie stearsa cu succes!");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 5) {
                    System.out.println();
                    System.out.print("Numar comunitati:\n");
                    System.out.println(userServicePrietenie.comunitati().size());
                }

                if (c == 6) {
                    System.out.println();
                    System.out.println("Cea mai sociabila comunitate:");
                    userServicePrietenie.ceaMaiSociabilaComunitate().forEach(System.out::println);
                }

                if (c == 7) {
                    System.out.print("Introduceti numele utilizatorului: ");
                    String nume = scanner.nextLine();
                    System.out.print("Introduceti prenumele utilizatorului: ");
                    String prenume = scanner.nextLine();

                    String date;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String dateTime = "";

                    System.out.println();

                    try {
                        List<Tuple<Utilizator, LocalDateTime>> lista = userServicePrietenie.getFriendDate(nume, prenume);
                        for (Tuple<Utilizator, LocalDateTime> util : lista) {
                            Utilizator utilizator = util.getLeft();
                            date = util.getRight().toString().replace('T', ' ');
                            dateTime = LocalDateTime.parse(date, formatter).toString().replace('T', ' ');

                            System.out.println("Prietenii lui " + nume + " " + prenume + " sunt:");
                            System.out.println("Prieten{" + "firstName='" + utilizator.getFirstName() + '\'' + ", lastName='" + utilizator.getLastName() + '\'' + dateTime + "}");
                        }
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 8) {
                    System.out.print("Introduceti numele utilizatorului: ");
                    String nume = scanner.nextLine();
                    System.out.print("Introduceti prenumele utilizatorului: ");
                    String prenume = scanner.nextLine();
                    System.out.print("Introduceti luna in care s-a creat prietenia: ");
                    String luna = scanner.nextLine();

                    String date;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String dateTime = "";

                    System.out.println();

                    try {
                        List<Tuple<Utilizator, LocalDateTime>> lista = userServicePrietenie.getFriendDateMonth(nume, prenume, luna);
                        System.out.println("Prietenii lui " + nume + " " + prenume + " sunt:");
                        for (Tuple<Utilizator, LocalDateTime> util : lista) {
                            Utilizator utilizator = util.getLeft();
                            date = util.getRight().toString().replace('T', ' ');
                            dateTime = LocalDateTime.parse(date, formatter).toString().replace('T', ' ');

                            System.out.println("Prieten{" + "firstName='" + utilizator.getFirstName() + '\'' + ", lastName='" + utilizator.getLastName() + '\'' + dateTime + "}");
                        }
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 9) {
                    System.out.print("Introduceti numele primului utilizator: ");
                    String nume1 = scanner.nextLine();
                    System.out.print("Introduceti prenumele primului utilizator ");
                    String prenume1 = scanner.nextLine();
                    Utilizator utilizator1 = new Utilizator(nume1, prenume1);

                    System.out.print("Introduceti numele celui de-al doilea utilizator: ");
                    String nume2 = scanner.nextLine();
                    System.out.print("Introduceti prenumele celui de-al doilea utilizator: ");
                    String prenume2 = scanner.nextLine();
                    Utilizator utilizator2 = new Utilizator(nume2, prenume2);

                    try {
                        List<Message> mesajeUtil = messageService.getUsersMessages(utilizator1, utilizator2);
                        System.out.println();
                        System.out.println("Mesajele utilizatorilor:");
                        for (Message mesaj : mesajeUtil) {
                            System.out.println(mesaj);
                            System.out.println();
                        }
                    }
                    catch (ValidationException e){
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 10) {
                    System.out.print("Introduceti numele expeditorului: ");
                    String nume1 = scanner.nextLine();
                    System.out.print("Introduceti prenumele expeditorului: ");
                    String prenume1 = scanner.nextLine();
                    Utilizator expeditor = new Utilizator(nume1, prenume1);

                    System.out.println("Introduceti numarul de destinatari: ");
                    Integer nr = scanner.nextInt();
                    scanner.nextLine();

                    List<Utilizator> listaDest = new LinkedList<>();
                    while (nr > 0) {
                        System.out.print("Introduceti numele destinatarului: ");
                        String nume2 = scanner.nextLine();
                        System.out.print("Introduceti prenumele destinatarului: ");
                        String prenume2 = scanner.nextLine();
                        Utilizator utilizator2 = new Utilizator(nume2, prenume2);
                        listaDest.add(utilizator2);
                        nr--;
                    }

                    System.out.print("Introduceti mesajul: ");
                    String mesaj = scanner.nextLine();

                    System.out.println("Introduceti data trimiterii(yyyy-MM-dd HH:mm): ");
                    String date = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                    try {
                        messageService.sendMessage(expeditor, listaDest, mesaj, dateTime);
                        System.out.println("Mesaj trimis cu succes!");
                    }
                    catch (ValidationException e){
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 11) {
                    System.out.println("Introduceti optiunea(Reply/Reply all): ");
                    String reply = scanner.nextLine();

                    System.out.println("Introduceti id-ul mesajului: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Introduceti numele expeditorului: ");
                    String nume = scanner.nextLine();
                    System.out.print("Introduceti prenumele expeditorului: ");
                    String prenume = scanner.nextLine();
                    Utilizator utilizator = new Utilizator(nume, prenume);

                    System.out.println("Introduceti mesajul de reply: ");
                    String mesaj = scanner.nextLine();
                    System.out.println("Introduceti data raspunsului(yyyy-MM-dd HH:mm): ");
                    String date = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                    if (reply.equals("Reply")) {
                        try {
                            messageService.replyMessage(id, utilizator, mesaj, dateTime);
                            System.out.println("Reply efectuat cu succes!");
                        }
                        catch (ValidationException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    if (reply.equals("Reply all")) {
                        try {
                            messageService.replyAllMessage(id, utilizator, mesaj, dateTime);
                            System.out.println("Reply all efectuat cu succes!");
                        }
                        catch (ValidationException e){
                            System.out.println(e.getMessage());
                        }
                    }
                }

                if (c == 12) {
                    System.out.print("Introduceti numele expeditorului: ");
                    String nume1 = scanner.nextLine();
                    System.out.print("Introduceti prenumele expeditorului: ");
                    String prenume1 = scanner.nextLine();
                    Utilizator expeditor = new Utilizator(nume1, prenume1);

                    System.out.print("Introduceti numele destinatarului: ");
                    String nume2 = scanner.nextLine();
                    System.out.print("Introduceti prenumele destinatarului: ");
                    String prenume2 = scanner.nextLine();
                    Utilizator destinatar = new Utilizator(nume2, prenume2);

                    System.out.println("Introduceti data trimiterii cererii(yyyy-MM-dd HH:mm): ");
                    String date = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                    try {
                        friendRequestService.sendFriendRequest(expeditor, destinatar, dateTime);
                        System.out.println("Cerere de prietenie trimisa cu succes!");
                    }
                    catch (ValidationException e){
                        System.out.println(e.getMessage());
                    }
                }

                if (c == 13) {
                    System.out.print("Introduceti numele utilizatorului: ");
                    String nume = scanner.nextLine();
                    System.out.print("Introduceti prenumele utilizatorului: ");
                    String prenume = scanner.nextLine();
                    Utilizator utilizator = new Utilizator(nume, prenume);

                    List<Utilizator> cereriUtilizatori = friendRequestService.receivedFriendRequests(utilizator);
                    for (Utilizator util : cereriUtilizatori) {
                        System.out.println("Acceptati cererea de prietenie de la " + util.getFirstName() + " " + util.getLastName() + "?(DA/NU)");
                        String raspuns = scanner.nextLine();

                        if (raspuns.equals("DA")) {
                            try {
                                friendRequestService.acceptFriendRequest(util, utilizator);
                                System.out.println("Prietenie acceptata cu succes!");
                            } catch (ValidationException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        if (raspuns.equals("NU")) {
                            friendRequestService.rejectFriendRequest(util, utilizator);
                            System.out.println("Prietenie refuzata cu succes!");
                        }
                    }
                    System.out.println();
                    System.out.println("Nu mai sunt cereri de prietenie! ;)");
                }

                if (c == 14) {
                    System.out.println();
                    System.out.println("Ati iesit din aplicatie!");
                    System.exit(0);
                }

//                System.out.println();
//                System.out.println("Utilizatori:");
//                userService.getAllUsers().forEach(System.out::println);
//
//                System.out.println();
//                System.out.println("Prietenii:");
//                userServicePrietenie.getAllFriendships().forEach(System.out::println);
//
//                System.out.println("Mesaje:");
//                List<Message> mesaje = messageService.getAllMessages();
//                mesaje.forEach(System.out::println);
//
//                System.out.println("Recipients:");
//                List<Recipient> recipients = messageService.getAllRecipients();
//                recipients.forEach(System.out::println);
                }
            }
        catch(ValidationException e){
                System.out.println(e.getMessage());
            }
        }
}