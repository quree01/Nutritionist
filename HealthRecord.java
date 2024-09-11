package nutrition;

public class HealthRecord {
    private int userId;
    private float height;
    private float weight;
    private int age;

    // Getters
    public int getUserId() {
        return userId;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
