/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;

/**
 *
 * @author User
 */
public class Main {
    
    public static void main(String[] args) {
        boolean loop = true;
        Scanner choose = new Scanner(System.in);
        String choice= null;
        PredictMention pm = new PredictMention();
        // Load trained file
        pm.setTrainedData("train_teg.arff");
       
        while(loop){
            System.out.println("------------------ Menu ------------------");
            System.out.println("1- Para introducir manualmente el TEG\n2- Cargar TEG desde archivo (test_teg.arff)");
            System.out.println("------------------------------------------");
            choice = choose.nextLine();
            switch(choice){
                case "1":
                    String params[] = new String[3];
                    System.out.println("Introduce titulo:");
                    params[0] = choose.nextLine();
                    System.out.println("Introduce resumen:");
                    params[1] = choose.nextLine();
                    System.out.println("Introduce palabras claves:");
                    params[2] = choose.nextLine();
                    pm.setTestData(params[0], params[1], params[2]);
                    System.out.println("Prediciendo ...");
                    pm.predict();
                    System.out.println("\n\n");
                    break;
                case "2":
                    pm.setTestData("test_teg.arff");
                    System.out.println("Prediciendo ...");
                    pm.predict();
                    System.out.println("\n\n");
                    break;
                default:
                    loop = false;
                    break;
                    
            }
        }  
    }
    
}
