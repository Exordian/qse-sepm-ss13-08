package at.ac.tuwien.sepm.dao.hsqldb;

import java.io.*;
import java.util.Scanner;

/**
 * Author: MUTH Markus
 * Date: 5/23/13
 * Time: 9:41 PM
 * Description of class "EntityGenerator":
 */
public class EntityGenerator {
    static void read() {
        System.out.println("EntityGenerator.read() called ...");
        FileReader fr = null;
        try {
            fr = new FileReader(TestHelper.PATH+"insert0.sql");
        } catch (FileNotFoundException e) {
            System.out.println("could not init FileReader ... ");
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);

        String s = "";

        try {
            if(br.ready()) {
                while(s!=null){
                    s = br.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println("could not read file ... ");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(TestHelper.PATH+"insert0.sql"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        while(scanner.hasNext()) {
            System.out.print(scanner.next());
        }
    }


}
