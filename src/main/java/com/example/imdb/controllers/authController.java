package com.example.imdb.controllers;

import com.example.imdb.classes.LoginDTO;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("auth")
public class authController {
    @PostMapping
    @RequestMapping("register")
    public ResponseEntity<String> register(@RequestBody LoginDTO login) {
        File myObj = new File("credentials.txt");
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("credentials.json");
            myWriter.write(new Gson().toJson(login));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error registering");
        }

        return ResponseEntity.ok("Successfully wrote to the file.");
    }

    @PostMapping
    @RequestMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO login) {
        try {
            File file = new File("credentials.json");
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder json = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null)
                json.append(st);


            LoginDTO correctLogin = new Gson().fromJson(json.toString(), LoginDTO.class);

            if (login.getUsername().equals(correctLogin.getUsername()) && login.getPassword().equals(correctLogin.getPassword())) {
                return ResponseEntity.ok("Correct credentials");
            }
            else{
                return ResponseEntity.badRequest().body("Wrong credentials");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Error");

    }
}
