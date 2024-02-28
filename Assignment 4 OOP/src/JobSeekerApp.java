import java.util.*;

interface Observer {
    void update(List<String> jobList);
}

interface Subject {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}

class JobMagazine implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private final List<String> jobPositions = new ArrayList<>();


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


    public void notifyObservers() {
        List<String> currentJobList = Collections.unmodifiableList(new ArrayList<>(jobPositions));
        for (Observer observer : observers) {
            observer.update(currentJobList);
        }
    }

    public void postJob(String newJob) {
        jobPositions.add(newJob);
        notifyObservers();
    }

    public void removeJob(String jobToRemove) {
        jobPositions.remove(jobToRemove);
        notifyObservers();
    }

    public List<String> getJobPositions() {
        return Collections.unmodifiableList(jobPositions);
    }
}

class JavaDeveloper implements Observer {
    private final String developerName;
    private final Map<Integer, String> applicableJobs = new HashMap<>();

    public JavaDeveloper(String developerName) {
        this.developerName = developerName;
    }


    public void update(List<String> jobList) {
        applicableJobs.clear();
        int jobNumber = 1;
        for (String job : jobList) {
            applicableJobs.put(jobNumber++, job);
        }
        System.out.println(developerName + " received updated job list: " + applicableJobs);
    }

    public Map<Integer, String> getApplicableJobs() {
        return Collections.unmodifiableMap(applicableJobs);
    }
}


public class JobSeekerApp {
    public static void main(String[] args) {

        JobMagazine magazine = new JobMagazine();


        JavaDeveloper developer = new JavaDeveloper("JavaDeveloper1");


        magazine.addObserver(developer);


        magazine.postJob("Java Developer");
        magazine.postJob("Software Engineer");
        magazine.postJob("Web Developer");


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOptions: \n1. Post a new job \n2. Remove a job \n3. Exit");

            System.out.print("Enter your choice (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter new job position: ");
                String newJob = scanner.nextLine();
                magazine.postJob(newJob);
            }
            else if (choice == 2) {
                System.out.print("Enter position to remove: ");
                String jobToRemove = scanner.nextLine();
                magazine.removeJob(jobToRemove);
            }
            else if (choice == 3) {
                System.out.println("Exiting");
                scanner.close();
                System.exit(0);
            }
            else {
                System.out.println("Invalid choice; Enter a number from 1-4");
            }
        }
    }
}