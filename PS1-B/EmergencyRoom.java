import java.util.*;
import java.io.*;

// write list of collaborators here: 
// year 2018 hash code: tPW3cEr39msnZUTL2L5J (do NOT delete this line)

class EmergencyRoom {

    private int arrivalNumber = 0;

    PriorityQueue<Patient> patientList;

    public EmergencyRoom() {
        patientList = new PriorityQueue<Patient>(1, new PatientComp());
    }

    // Adds patient to queue.
    void ArriveAtHospital(String patientName, int emergencyLvl) {
        arrivalNumber++;
        patientList.add(new Patient(patientName, emergencyLvl, arrivalNumber));
    }

    void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
    }

    // Treats patient and remove patient from list.
    void Treat(String patientName) {
        patientList.poll();
    }

    // Returns the name of the most important patient.
    String Query() {
        String ans = "The emergency suite is empty";
        if (!patientList.isEmpty()) {
            return patientList.peek().getName();
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
