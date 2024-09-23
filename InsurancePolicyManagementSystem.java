import java.time.LocalDate;
import java.util.*;

class Policy {
    private String policyNumber;
    private String policyholderName;
    private LocalDate expiryDate;
    private String coverageType;
    private double premiumAmount;

    public Policy(String policyNumber, String policyholderName, LocalDate expiryDate, String coverageType, double premiumAmount) {
        this.policyNumber = policyNumber;
        this.policyholderName = policyholderName;
        this.expiryDate = expiryDate;
        this.coverageType = coverageType;
        this.premiumAmount = premiumAmount;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPolicyholderName() {
        return policyholderName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policyNumber='" + policyNumber + '\'' +
                ", policyholderName='" + policyholderName + '\'' +
                ", expiryDate=" + expiryDate +
                ", coverageType='" + coverageType + '\'' +
                ", premiumAmount=" + premiumAmount +
                '}';
    }
}

class PolicyManager {
    HashMap<String, Policy> policiesMap;
    private LinkedHashMap<String, Policy> orderedPoliciesMap;
    private TreeMap<LocalDate, List<Policy>> sortedPoliciesMap;

    public PolicyManager() {
        policiesMap = new HashMap<>();
        orderedPoliciesMap = new LinkedHashMap<>();
        sortedPoliciesMap = new TreeMap<>();
    }

    // Add a policy
    public void addPolicy(Policy policy) {
        policiesMap.put(policy.getPolicyNumber(), policy);
        orderedPoliciesMap.put(policy.getPolicyNumber(), policy);
        
        // Add to the sorted map
        sortedPoliciesMap.putIfAbsent(policy.getExpiryDate(), new ArrayList<>());
        sortedPoliciesMap.get(policy.getExpiryDate()).add(policy);
    }

    // Retrieve a policy by its number
    public Policy getPolicyByNumber(String policyNumber) {
        return policiesMap.get(policyNumber);
    }

    // List all policies expiring within the next 30 days
    public List<Policy> listPoliciesExpiringSoon() {
        List<Policy> expiringPolicies = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysFromNow = today.plusDays(30);

        for (Map.Entry<LocalDate, List<Policy>> entry : sortedPoliciesMap.entrySet()) {
            if (entry.getKey().isAfter(today) && entry.getKey().isBefore(thirtyDaysFromNow)) {
                expiringPolicies.addAll(entry.getValue());
            }
        }
        return expiringPolicies;
    }

    // List all policies for a specific policyholder
    public List<Policy> listPoliciesByHolder(String policyholderName) {
        List<Policy> holderPolicies = new ArrayList<>();
        for (Policy policy : policiesMap.values()) {
            if (policy.getPolicyholderName().equalsIgnoreCase(policyholderName)) {
                holderPolicies.add(policy);
            }
        }
        return holderPolicies;
    }

    // Remove expired policies
    public void removeExpiredPolicies() {
        LocalDate today = LocalDate.now();
        Iterator<Map.Entry<LocalDate, List<Policy>>> iterator = sortedPoliciesMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<LocalDate, List<Policy>> entry = iterator.next();
            if (entry.getKey().isBefore(today)) {
                for (Policy policy : entry.getValue()) {
                    policiesMap.remove(policy.getPolicyNumber());
                    orderedPoliciesMap.remove(policy.getPolicyNumber());
                }
                iterator.remove(); // Remove from sorted map
            }
        }
    }
}

public class InsurancePolicyManagementSystem {
    public static void main(String[] args) {
        PolicyManager policyManager = new PolicyManager();

        // Adding some policies
        policyManager.addPolicy(new Policy("P001", "Alice Smith", LocalDate.now().plusDays(15), "Health", 1200.00));
        policyManager.addPolicy(new Policy("P002", "Bob Johnson", LocalDate.now().plusDays(45), "Auto", 800.00));
        policyManager.addPolicy(new Policy("P003", "Alice Smith", LocalDate.now().plusDays(25), "Home", 600.00));
        policyManager.addPolicy(new Policy("P004", "Charlie Brown", LocalDate.now().minusDays(5), "Health", 1000.00)); // Expired

        // Retrieving a policy
        System.out.println("Policy P001: " + policyManager.getPolicyByNumber("P001"));

        // Listing policies expiring soon
        System.out.println("Policies expiring within the next 30 days: " + policyManager.listPoliciesExpiringSoon());

        // Listing policies by policyholder
        System.out.println("Policies for Alice Smith: " + policyManager.listPoliciesByHolder("Alice Smith"));

        // Removing expired policies
        policyManager.removeExpiredPolicies();
        System.out.println("Policies after removing expired ones: " + policyManager.policiesMap);
    }
}
