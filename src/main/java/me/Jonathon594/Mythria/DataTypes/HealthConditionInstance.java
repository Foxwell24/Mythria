package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Managers.HealthManager;

public class HealthConditionInstance {
    private HealthCondition condition;
    private int age = 0;
    private int cureStep = 0;
    private double severity;

    public HealthConditionInstance(final HealthCondition condition, final double severity) {
        super();
        this.condition = condition;
        this.severity = severity;
    }

    public static HealthConditionInstance fromString(final String s) {
        final String[] parts = s.split("|");
        if (parts.length == 4) {
            final HealthConditionInstance hci = new HealthConditionInstance(
                    HealthManager.getHealthConditionByName(parts[0]), Double.parseDouble(parts[3]));
            hci.setAge(Integer.parseInt(parts[1]));
            hci.setCureStep(Integer.parseInt(parts[2]));
            return hci;
        }
        return null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof HealthConditionInstance))
            return false;
        final HealthConditionInstance other = (HealthConditionInstance) obj;
        return condition.getName().equals(other.getCondition().getName());
    }

    public HealthCondition getCondition() {
        return condition;
    }

    public HealthConditionInstance setCondition(final HealthCondition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public String toString() {
        return condition.getName() + "|" + age + "|" + cureStep + "|" + severity;
    }

    public int getAge() {
        return age;
    }

    public HealthConditionInstance setAge(final int age) {
        this.age = age;
        return this;
    }

    public int getCureStep() {
        return cureStep;
    }

    public HealthConditionInstance setCureStep(final int cureStep) {
        this.cureStep = cureStep;
        return this;
    }

    public double getSeverity() {
        return severity;
    }

    public HealthConditionInstance setSeverity(final double severity) {
        this.severity = severity;
        return this;
    }

    public CureCondition getNextCureStep() {
        if(getCondition().getCureConditions().size() < cureStep + 1) return null;
        CureCondition c = getCondition().getCureConditions().get(cureStep);
        return c;
    }

    public boolean isCured() {
        return cureStep >= getCondition().getCureConditions().size();
    }
}
