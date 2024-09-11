package nutrition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {
    public boolean addRecipe(Recipe recipe) {
        String sql = "INSERT INTO recipes (title, ingredients, instructions, user_id, created_at) VALUES (?, ?, ?, ?, NOW())";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recipe.getTitle());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getInstructions());
            statement.setInt(4, recipe.getUserId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Recipe> getRecipes(int userId) {
        String sql = "SELECT * FROM recipes WHERE user_id = ?";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getInt("id"));
                recipe.setTitle(resultSet.getString("title"));
                recipe.setIngredients(resultSet.getString("ingredients"));
                recipe.setInstructions(resultSet.getString("instructions"));
                recipe.setUserId(resultSet.getInt("user_id"));
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
