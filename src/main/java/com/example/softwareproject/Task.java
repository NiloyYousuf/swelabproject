package com.example.softwareproject;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String name;
    private int noOfWeeks;

    private int startWeek; // New field for start week
    private int endWeek;   // New field for end week

    public Task(String name, int startWeek, int endWeek) {
        this.name = name;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        // Set default values for other fields if needed
    }
    private List<String> dependencies = new ArrayList<>();


    public Task(String name, int numberOfWeeks, List<String> dependencies) {
        this.name = name;
        this.noOfWeeks = numberOfWeeks;
        this.dependencies.addAll(dependencies);
    }



    public Task(String name) {
        this.name=name;
    }

    public Task(String a, int i) {
        this.name=a;
        this.noOfWeeks=i;
    }


    public static void printAllTasks() {
        for (Task task : TaskUtils.tasks) {
            System.out.println("\nTask Name: " + task.getName());
            System.out.println("Number of Weeks: " + task.getNoOfWeeks());
            System.out.print("Dependencies: ");
            task.getDependencieslist().forEach(dependency -> System.out.print(dependency + " "));
            System.out.println("\n"); // Add a separator between tasks
        }
    }

    public void addDependency(String dependency) {
        this.dependencies.add(dependency);
        //this.dependencies_task.add(getTaskByName(dependency));
    }


    public List<String> getDependencieslist() {
        return dependencies;
    }

    public String getDependencies() {

        return String.join(" ", dependencies);
    }

    public void setNoOfWeeks(int weeks) {
        this.noOfWeeks = weeks;
    }

    public int getNoOfWeeks() {
        return noOfWeeks;
    }

    public String getName() {
        return name;
    }




    public  Task getTaskByName(String taskName) {
        for (Task task : TaskUtils.tasks) {
            if (task.getName().equals(taskName)) {
                return task;
            }
        }
        return null; // Task not found
    }






}
