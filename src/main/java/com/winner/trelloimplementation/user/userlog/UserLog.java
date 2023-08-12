package com.winner.trelloimplementation.user.userlog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserLog {
    public static ArrayList<String> fileReader() {
        String path = "logs/springStudy.log";

        File file = new File(path);
        ArrayList<String> temp = new ArrayList<>();
        String logline = "";

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                logline = scan.nextLine();
                if (logline.startsWith("Secured") && !logline.contains("GET")) {
                    temp.add(logline);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("fileReader 에러 : " + e);
        }

        return temp;
    }
}
