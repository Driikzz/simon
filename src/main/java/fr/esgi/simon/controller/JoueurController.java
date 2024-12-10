package fr.esgi.simon.controller;

import fr.esgi.simon.business.Joueur;
import fr.esgi.simon.service.JoueurService;
import fr.esgi.simon.service.impl.JoueurServiceImpl;

import java.util.Scanner;

public class JoueurController {
    private final JoueurService joueurService = new JoueurServiceImpl();

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Créer un joueur");
            System.out.println("2. Obtenir un joueur");
            System.out.println("3. Lister tous les joueurs");
            System.out.println("4. Supprimer un joueur");
            System.out.println("5. Quitter");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("Entrez le pseudo du joueur : ");
                    String pseudo = scanner.nextLine();
                    Joueur joueur = joueurService.creerJoueur(pseudo);
                    System.out.println("Joueur créé : " + joueur);
                    break;
                case 2:
                    System.out.println("Entrez l'ID du joueur : ");
                    Long id = scanner.nextLong();
                    Joueur joueurTrouve = joueurService.obtenirJoueur(id);
                    System.out.println("Joueur trouvé : " + joueurTrouve);
                    break;
                case 3:
                    System.out.println("Liste des joueurs : ");
                    joueurService.listerTousLesJoueurs().forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Entrez l'ID du joueur à supprimer : ");
                    Long idASupprimer = scanner.nextLong();
                    joueurService.supprimerJoueur(idASupprimer);
                    System.out.println("Joueur supprimé.");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}
