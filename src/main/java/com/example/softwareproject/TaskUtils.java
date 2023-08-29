package com.example.softwareproject;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskUtils {

    public  static ArrayList<Task> tasks;
    public static ArrayList<Task> convertToArrayList(ObservableList<Task> taskList) {
        tasks = new ArrayList<>(taskList);
        return tasks;
    }
}