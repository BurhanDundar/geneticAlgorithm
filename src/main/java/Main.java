
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author burhandundar
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GeneticAlgo passwordCracker1 = new GeneticAlgo("Deep Learning 2022",1000);
        passwordCracker1.runAlgo();
        System.out.println();
        GeneticAlgo passwordCracker2 = new GeneticAlgo("Deep Learning 2022",1000);
        passwordCracker2.runAlgo();
        System.out.println();
        GeneticAlgo passwordCracker3 = new GeneticAlgo("Deep Learning 2022",1000);
        passwordCracker3.runAlgo();
        System.out.println();
        System.out.println("Nesil ortalaması: " + passwordCracker1.generationMean / 3);
        System.out.println();
        // Kromozom sayısı azaltılırsa
        System.out.println("Kromozom sayısı azaltıldığında");
        GeneticAlgo passwordCracker4 = new GeneticAlgo("Deep Learning 2022",100);
        passwordCracker4.runAlgo();
        System.out.println();
        GeneticAlgo passwordCracker5 = new GeneticAlgo("DeepLearning",1000);
        passwordCracker5.runAlgo();
        
    }
}
