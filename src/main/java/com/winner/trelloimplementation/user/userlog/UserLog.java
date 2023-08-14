package com.winner.trelloimplementation.user.userlog;

import com.winner.trelloimplementation.user.entity.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserLog {
    public static final String logPath = "logs/springStudy.log";

    public static ArrayList<String> fileReader(User user) {
        String path = logPath;

        File file = new File(path);

        ArrayList<String> temp = new ArrayList<>();
        String logline = "";

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                logline = scan.nextLine();
                if (logline.startsWith("Secured") && !logline.contains("GET")) {
                    temp.add(user.getUsername() + " " + logline);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("fileReader 에러 : " + e);
        }

        return temp;
    }

    public static void saveLogs(User user) {
        String path2 = "logs/";
        String path3 = "logs/userlog - " + user.getUsername() + ".log";
        String fileName = "userlog - " + user.getUsername() + ".log";

        File file2 = new File(path2);
        File file3 = new File(path3);

        if (!file2.exists()) {
            // 경로가 없다면 생성합니다. (디렉토리)
            try
            {
                file2.mkdirs();
            }
            catch (Exception e)
            {
                System.out.println("path mkdirs Error : "+ e);
            }
        }

        FileWriter writer = null;
        try
        {
            // 기존 파일의 내용에 이어서 쓰려면 true를
            // 기존 내용을 없애고 새로 쓰려면 false를 지정한다.
            writer = new FileWriter(file2 + "/" + fileName, true);
            for(String userlog : fileReader(user)) {
                writer.write(userlog + System.lineSeparator());
            }
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("fileWriter 에러 : "+ e);
        }
        finally
        {
            try
            {
                if(writer != null)
                {
                    writer.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> fullUserLog(User user) {
        String path = "logs/userlog - " + user.getUsername() + ".log";

        File file = new File(path);

        ArrayList<String> temp = new ArrayList<>();
        String logline = "";

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                logline = scan.nextLine();
                temp.add(logline);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("fileReader 에러 : " + e);
        }

        return temp;
    }
}
