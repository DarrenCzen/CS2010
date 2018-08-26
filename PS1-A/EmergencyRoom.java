import java.util.*;
import java.io.*;

// write your matric number here: A0155600R
// write your name here: Darren Chin

// write list of collaborators here: 
// Henry Ang Tien Hock, for recommending linked list as a better option over priority queue for subtask A.

// year 2018 hash code: tPW3cEr39msnZUTL2L5J (do NOT delete this line)

class EmergencyRoom {

    public static final int MAX_SIZE = 10;

    private int arrivalNumber = 0;
    private LinkedList<Patient> patientList;

    public EmergencyRoom() {
        patientList = new LinkedList<Patient>();
    }

    // Adds patient to linked list.
    void ArriveAtHospital(String patientName, int emergencyLvl) {
        arrivalNumber++;
        patientList.add(new Patient(patientName, emergencyLvl, arrivalNumber));
        Collections.sort(patientList, new PatientComparator());
    }

    // Updates patient's emergency level and sort linked list.
    void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
        for (int i = 0; i < MAX_SIZE && i < patientList.size(); i++) {
            if (patientList.get(i).getName().equals(patientName)) {
                patientList.get(i).adjustLevel(incEmergencyLvl);
                break;
            }
        }

        Collections.sort(patientList, new PatientComparator());
    }

    // Treats patient and remove patient from list.
    void Treat(String patientName) {
        for (int j = 0; j < MAX_SIZE && j < patientList.size(); j++) {
            if (patientList.get(j).getName().equals(patientName)) {
                patientList.remove(j);
                break;
            }
        }
    }

    // Returns the name of the most important patient.
    String Query() {
        String ans = "The emergency suite is empty";
        if (!patientList.isEmpty()) {
            return patientList.getFirst().getName();
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
class PatientComparator implements Comparator<Patient> {

    @Override
    public int compare(Patient o1, Patient o2) {
        if (o1.equals(o2)){
            return 0;
        } else if (o1.getLevel() < o2.getLevel()) {
            return 1;
        } else if (o1.getLevel() == o2.getLevel() && (o1.getArrivalNumber() > o2.getArrivalNumber())){
            return 1;
        } 
        else {
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