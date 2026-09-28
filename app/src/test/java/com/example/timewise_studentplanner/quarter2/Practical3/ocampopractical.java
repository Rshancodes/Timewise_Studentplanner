package com.example.timewise_studentplanner.quarter2.Practical3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Package-private class Records (no 'public' modifier)
class Records {
    private Account[] accounts;
    private int accountCount;

    public Records() {
        this.accounts = new Account[10];
        this.accountCount = 0;
    }

    public void addAccount(Account account) {
        if (accountCount >= accounts.length) {
            Account[] newArray = new Account[accounts.length * 2];
            System.arraycopy(accounts, 0, newArray, 0, accounts.length);
            accounts = newArray;
        }
        accounts[accountCount] = account;
        accountCount++;
    }

    public Account findAccount(String name) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].studentName.equalsIgnoreCase(name)) {
                return accounts[i];
            }
        }
        return null;
    }
}

// Account class needed so Records can reference it
class Account {
    String studentName;
    int studentID;
    int studentPIN;
    int infractionTotal;
    Infraction[] infractions;

    private static int idCounter = 1000;

    public Account(String name, int PIN) {
        this.studentName = name;
        this.studentPIN = PIN;
        this.studentID = ++idCounter;
        this.infractionTotal = 0;
        this.infractions = new Infraction[10];
    }

    public boolean logIn(String name, int PIN) {
        if (this.studentName.equalsIgnoreCase(name) && this.studentPIN == PIN) {
            System.out.println("success");
            return true;
        } else {
            System.out.println("Login failed: Incorrect name or PIN.");
            return false;
        }
    }

    public void addInfractionToRecord(Infraction infraction) {
        if (infractionTotal >= infractions.length) {
            Infraction[] newArray = new Infraction[infractions.length * 2];
            System.arraycopy(infractions, 0, newArray, 0, infractions.length);
            infractions = newArray;
        }
        infractions[infractionTotal] = infraction;
        infractionTotal++;
    }

    public void record() {
        System.out.println("\n--- STUDENT RECORD ---");
        System.out.println("Student Name: " + studentName);
        System.out.println("Student ID:   " + studentID);
        System.out.println("Total Infractions: " + infractionTotal);
        System.out.println("----------------------");

        if (infractionTotal == 0) {
            System.out.println("No infractions recorded.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < infractionTotal; i++) {
                Infraction inf = infractions[i];
                System.out.println((i + 1) + ". " + inf.infractionName
                        + " (Degree: " + inf.infractionDegree + ") - Date: "
                        + inf.infractionTime.format(formatter));
            }
        }
        System.out.println("----------------------\n");
    }
}

// Infraction class needed by Account
class Infraction {
    String infractionName;
    int infractionDegree;
    LocalDateTime infractionTime;

    public Infraction(String infractionName, int infractionDegree, LocalDateTime infractionTime) {
        this.infractionName = infractionName;
        this.infractionDegree = infractionDegree;
        this.infractionTime = infractionTime;
    }

    public static void add(String infractionName, int degree, LocalDateTime now, Account student) {
        Infraction newInfraction = new Infraction(infractionName, degree, now);
        student.addInfractionToRecord(newInfraction);
    }
}

class ocampo {
    public static void main(String[] args) {
        Records systemRecords = new Records();
        Account acc = new Account("John Doe", 1234);
        systemRecords.addAccount(acc);

        Account found = systemRecords.findAccount("John Doe");
        if (found != null && found.logIn("John Doe", 1234)) {
            Infraction.add("Tardiness", 1, LocalDateTime.now(), found);
            found.record();
        }
    }
}