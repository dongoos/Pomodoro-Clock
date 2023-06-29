package com.example.tomato;



public class FUser {
    private  String name;
    private  String password;
    private  String email;
    private  int id;
    private  String unLockId;
    private  int score;



    public FUser(String name, String email, int id, String unLockId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.id = id;
        this.unLockId = unLockId;

    }

    public FUser() {

    }


    public  int getScore() {
        return score;
    }

    public  void setScore(int score) {
        this.score = score;
    }
    // Getters
    public  String getName() {
        return name;
    }

    public  String getPassword() {
        return password;
    }

    public  String getEmail() {
        return email;
    }

    public  int getId() {
        return id;
    }

    public  String getUnLockId() {
        return unLockId;
    }

    // Setters
    public  void setName(String name) {
        this.name = name;
    }

    public  void  setPassword(String password) {
        this.password = password;
    }

    public  void setEmail(String email) {
        this.email = email;
    }

    public  void setId(int id) {
        this.id= id;
    }

    public  void setUnLockId(String unLockId) {
        this.unLockId = unLockId;
    }


}
