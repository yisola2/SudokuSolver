package rules;

public class RuleFactory {
    private static RuleFactory instance;
    
    private RuleFactory() {
        // Private constructor to enforce singleton pattern
    }
    
    public static synchronized RuleFactory getInstance() {
        if (instance == null) {
            instance = new RuleFactory();
        }
        return instance;
    }
    
    /**
     * Creates a deduction rule based on the rule type.
     * @param ruleType The type of rule to create ("DR1", "DR2", or "DR3")
     * @return A new instance of the requested rule
     * @throws IllegalArgumentException if the rule type is unknown
     */
    public DeductionRule createRule(String ruleType) {
        return switch (ruleType.toUpperCase()) {
            case "DR1" -> new DR1();
            case "DR2" -> new DR2();
            case "DR3" -> new DR3();
            default -> throw new IllegalArgumentException("Unknown rule type: " + ruleType);
        };
    }
}