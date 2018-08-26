import java.util.*;
import java.io.*;

// write your matric number here: A0155600R
// write your name here: Darren Chin

// write list of collaborators here:
// While discovering and understanding algorithm of how to extract an element from priority queue with its property
// The three websites referenced below help me to understand that a treeset might be more helpful in extracting 
// elements in a sorted order as compared to a priority queue as it a a prior queue only maintains the order of the front and back.
// By using a treeset, we can search, remove in O(log n) complexity as compared to a queue which uses complexity O(n).
// In addition a hashmap is used to gain hold of the reference of the patients
// https://stackoverflow.com/questions/10404015/searching-for-a-specific-element-in-a-treeset
// https://javarevisited.blogspot.com/2017/04/difference-between-priorityqueue-and-treeset-in-java.html
// https://www.geeksforgeeks.org/implement-priorityqueue-comparator-java/

// year 2018 hash code: tPW3cEr39msnZUTL2L5J (do NOT delete this line)

class EmergencyRoom {

    private int arrivalNumber = 1;

    private TreeSet<Patient> patientList;
    private HashMap<String, Patient> patientMap;

    public EmergencyRoom() {
        patientList = new TreeSet<Patient>(new PatientComp());
        patientMap = new HashMap<String, Patient>();
    }

    // Adds patient to treeset and map.
    void ArriveAtHospital(String patientName, int emergencyLvl) {
        arrivalNumber++;
        Patient patient = new Patient(patientName, emergencyLvl, arrivalNumber);
        patientList.add(patient);
        patientMap.put(patientName, patient);
    }

    // Updates patient's emergency level and reorder TreeSet.
    void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
        // Obtain reference of the patient and adjust his emergency level
        Patient updatePatient = patientMap.get(patientName);

        //patient is removed to readjust the order of the sorted treeset
        patientList.remove(updatePatient);
        updatePatient.adjustLevel(incEmergencyLvl);
        patientList.add(updatePatient);
    }

    // Treats patient and remove patient from list.
    void Treat(String patientName) {
        Patient updatePatient = patientMap.get(patientName);
        patientList.remove(updatePatient);
        patientMap.remove(patientName);
    }

    // Returns the name of the most important patient.
    String Query() {
        String ans = "The emergency suite is empty";
        if (!patientList.isEmpty()) {
            return patientList.first().getName();
        }
        return ans;
    }

    void run() throws Exception {
        // do not alter this method

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
        while (numCMD-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            switch (command) {
            case 0:
                ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken()));
                break;
            case 1:
                UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken()));
                break;
            case 2:
                Treat(st.nextToken());
                break;
            case 3:
                pr.println(Query());
                break;
            }
        }
        pr.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        EmergencyRoom ps1 = new EmergencyRoom();
        ps1.run();
    }
}

// Custom Comparator to compare patient's emergency level & arrival number.
class PatientComp implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
        if (o1.equals(o2)) {
            return 0;
        } else if (o1.getLevel() < o2.getLevel()) {
            return 1;
        } else if (o1.getLevel() == o2.getLevel() && o1.getArrivalNumber() > o2.getArrivalNumber()) {
            return 1;
        } else {
            return -1;
        }
    }
}

class Patient {
    private String patientName;
    private int emergencyLvl;
    private int arrivalNumber;

    public Patient(String name, int eLvl, int arrivalNumber) {
        this.patientName = name;
        this.emergencyLvl = eLvl;
        this.arrivalNumber = arrivalNumber;
    }

    public String getName() {
        return this.patientName;
    }

    public int getLevel() {
        return this.emergencyLvl;
    }

    public int getArrivalNumber() {
        return this.arrivalNumber;
    }

    public void adjustLevel(int value) {
        this.emergencyLvl += value;
    }
}