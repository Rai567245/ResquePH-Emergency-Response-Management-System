package application;

import java.util.Comparator;

public class PatientPriority {

	public static final Comparator<Patient> COMPARATOR = (a, b) -> {

	    String tA = a.getTriageLevel();
	    String tB = b.getTriageLevel();

	    if (tA == null && tB == null) return 0;
	    if (tA == null) return 1;
	    if (tB == null) return -1;

	    int triageA = Integer.parseInt(tA.substring(1));
	    int triageB = Integer.parseInt(tB.substring(1));

	    int result = Integer.compare(triageA, triageB);
	    if (result != 0) return result;

	    return Integer.compare(a.getQueueNo(), b.getQueueNo());
	};


}