package com.example.tomato;

public class User {
    private static String name;
    private static String password;
    private static String email;
    private static int uid;
    private static int unLockId;

    public User(String name, String email, int uid, int unLockId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.uid = uid;
        this.unLockId = unLockId;
    }

    public User() {

    }

    // User session methods
    public static void setUserSession(String name, String email, int uid, String password) {
        setEmail(email);
        setName(name);
        setUid(uid);
        setPassword(password);
    }

    public void clearUserSession() {

    }
    // Getters
    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }

    public static String getEmail() {
        return email;
    }

    public static int getUid() {
        return uid;
    }

    public static int getUnLockId() {
        return unLockId;
    }

    // Setters
    public static void setName(String name) {
        User.name = name;
    }

    public static void  setPassword(String password) {
        User.password = password;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static void setUid(int uid) {
        User.uid = uid;
    }

    public static void setUnLockId(int unLockId) {
        User.unLockId = unLockId;
    }


}
