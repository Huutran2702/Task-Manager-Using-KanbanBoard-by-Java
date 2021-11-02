package com.example.kanbanboard;

import com.example.kanbanboard.repository.UserRepository;
import com.example.kanbanboard.service.JacksonParser;

import java.io.*;

public class FileSevice {
   public static String read( String fileName) throws IOException {
       FileReader reader = new FileReader(fileName);
       BufferedReader buffer = new BufferedReader(reader);
       return buffer.readLine();
   }
   public static void write(UserRepository userRepository, String fileName) throws IOException {
       FileWriter writer = new FileWriter(fileName);
       BufferedWriter bufferedWriter = new BufferedWriter(writer);
       String json = JacksonParser.INSTANCE.toJson(userRepository.userList);
       bufferedWriter.write(json);
       bufferedWriter.close();
   }
}
