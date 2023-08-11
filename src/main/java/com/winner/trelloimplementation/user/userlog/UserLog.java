package com.winner.trelloimplementation.user.userlog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserLog {
    public static String fileReader() throws Exception{
        String path = "logs/springStudy.log";

        File file = new File(path);
        String temp = "";
        String logline = "";

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                logline = scan.nextLine();
                if (logline.startsWith("Secured")) {
                    if (!(logline.contains("/api/sign") || logline.contains("/js") || logline.contains("/css") || logline.contains("/favicon") || logline.contains(""))) {
                        System.out.println(scan.nextLine());
                        temp += scan.nextLine();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("fileReader 에러 : " + e.toString());
        }

        return temp;
    }
}
