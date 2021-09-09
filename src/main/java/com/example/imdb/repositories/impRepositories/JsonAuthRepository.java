package com.example.imdb.repositories.impRepositories;

import com.example.imdb.classes.LoginDTO;
import com.example.imdb.repositories.AuthRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class JsonAuthRepository implements AuthRepository {
    public static final String filePath = "credentials.json";

    @Override
    public int register(LoginDTO login) {
        File myObj = new File(filePath);
        ArrayList<LoginDTO> listOfCredentials;
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(myObj));
            StringBuilder json = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null)
                json.append(st);


            listOfCredentials = new Gson().fromJson(json.toString(), new TypeToken<ArrayList<LoginDTO>>() {
            }.getType());
            if(listOfCredentials == null){
                listOfCredentials = new ArrayList<>();
            }
            listOfCredentials.add(login);

        } catch (IOException e) {
            return -2;
        }

        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(new Gson().toJson(listOfCredentials));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -3;
        }

        return 1;

    }

    @Override
    public int login(LoginDTO login) {
        File myObj = new File(filePath);
        ArrayList<LoginDTO> listOfCredentials;
        try {
            BufferedReader br = new BufferedReader(new FileReader(myObj));
            StringBuilder json = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null)
                json.append(st);


            listOfCredentials = new Gson().fromJson(json.toString(), new TypeToken<ArrayList<LoginDTO>>() {
            }.getType());
            if(listOfCredentials == null){
                removeCurrentUser();
                return -1;
            }
            Optional<LoginDTO> optCred = listOfCredentials.stream().filter(l -> l.getUsername().equals(login.getUsername())).findFirst();
            if(optCred.isPresent()){
                LoginDTO loginFromJson = optCred.get();
                if(login.getPassword().equals(loginFromJson.getPassword())){
                    try {
                        FileWriter myWriter = new FileWriter("login.json");
                        myWriter.write(new Gson().toJson(login));
                        myWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        removeCurrentUser();
                        return -2;
                    }
                    return 1;
                }
                else{
                    removeCurrentUser();
                    return 0;
                }
            }
        } catch (IOException e) {
            removeCurrentUser();
            return -1;
        }
        removeCurrentUser();
        return -1;
    }

    @Override
    public boolean checkUserExists(LoginDTO login) {
        File myObj = new File(filePath);
        ArrayList<LoginDTO> listOfCredentials;
        try {
            BufferedReader br = new BufferedReader(new FileReader(myObj));
            StringBuilder json = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null)
                json.append(st);


            listOfCredentials = new Gson().fromJson(json.toString(), new TypeToken<ArrayList<LoginDTO>>() {
            }.getType());
            if(listOfCredentials == null){
                return false;
            }
            Optional<LoginDTO> optCred = listOfCredentials.stream().filter(l -> l.getUsername().equals(login.getUsername())).findFirst();
            return optCred.isPresent();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public LoginDTO getCurrentUser() {
        File myObj = new File("login.json");
        LoginDTO cred;
        try {
            BufferedReader br = new BufferedReader(new FileReader(myObj));
            StringBuilder json = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null)
                json.append(st);


            cred = new Gson().fromJson(json.toString(), LoginDTO.class);
            return cred;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void removeCurrentUser() {
        try {
            FileWriter myWriter = new FileWriter("login.json");
            myWriter.write("");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String EncryptPassword(String password) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
