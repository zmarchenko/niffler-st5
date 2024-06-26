package guru.qa.niffler.data.repository.spend;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import guru.qa.niffler.data.sjdbc.CategoryEntityRowMapper;
import guru.qa.niffler.data.sjdbc.SpendEntityRowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static guru.qa.niffler.data.Database.SPEND;


public class SpendRepositoryJdbc implements SpendRepository {

    private static final DataSource spendDataSource = DataSourceProvider.dataSource(SPEND);

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO \"category\" (category, username) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {

            preparedStatement.setString(1, category.getCategory());
            preparedStatement.setString(2, category.getUsername());
            preparedStatement.executeUpdate();

            UUID generatedId;

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can't access to id");
                }
            }

            category.setId(generatedId);
            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CategoryEntity> findAllByCategoryName(String categoryName) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (
                             "SELECT * FROM category WHERE category = ?"
                     )) {

            statement.setString(1, categoryName);

            ResultSet resultSet = statement.executeQuery();

            List<CategoryEntity> categoryEntities = new ArrayList<>();

            while (resultSet.next()) {
                CategoryEntity category = CategoryEntityRowMapper.INSTANCE.mapRow(resultSet, 0);
                categoryEntities.add(category);
            }

            return categoryEntities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {

        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(
                     "UPDATE \"category\" SET category = ?, username = ? WHERE id = ?")) {

            updateStatement.setString(1, category.getCategory());
            updateStatement.setString(2, category.getUsername());
            updateStatement.setObject(3, category.getId());
            updateStatement.executeUpdate();

            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM \"category\" WHERE ID = ?"
             )) {
            preparedStatement.setObject(1, category.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO spend (username, spend_date, currency, amount, description, category_id) VALUES (?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {

            preparedStatement.setString(1, spend.getUsername());
            preparedStatement.setDate(2, new Date(spend.getSpendDate().getTime()));
            preparedStatement.setString(3, spend.getCurrency().name());
            preparedStatement.setDouble(4, spend.getAmount());
            preparedStatement.setString(5, spend.getDescription());
            preparedStatement.setObject(6, spend.getCategory().getId());
            preparedStatement.executeUpdate();

            UUID generatedId;

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalStateException("Can't access to id");
                }
            }
            spend.setId(generatedId);
            return spend;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity editSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(""" 
                     UPDATE spend SET
                     username=?,
                     spend_date=?,
                     currency=?,
                     amount=?,
                     description=?,
                     category_id=?
                     WHERE id = ?""")) {

            updateStatement.setObject(1, spend.getUsername());
            updateStatement.setDate(2, new Date(spend.getSpendDate().getTime()));
            updateStatement.setString(3, spend.getCurrency().name());
            updateStatement.setObject(4, spend.getAmount());
            updateStatement.setObject(5, spend.getDescription());
            updateStatement.setObject(6, spend.getCategory().getId());
            updateStatement.setObject(7, spend.getId());
            updateStatement.executeUpdate();

            return spend;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM spend WHERE ID = ?"
             )) {
            preparedStatement.setObject(1, spend.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SpendEntity> findAllByUsername(String username) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM spend WHERE username = ?"
             )) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<SpendEntity> spendEntities = new ArrayList<>();

            while (resultSet.next()) {
                SpendEntity spend = SpendEntityRowMapper.INSTANCE.mapRow(resultSet, 0);
                spendEntities.add(spend);
            }

            return spendEntities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SpendEntity findAByUsernameAndDescription(String username, String description) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM spend WHERE username = ? AND description = ?"
             )) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, description);

            ResultSet resultSet = preparedStatement.executeQuery();

            SpendEntity spend = null;

            while (resultSet.next()) {
                spend = SpendEntityRowMapper.INSTANCE.mapRow(resultSet, 0);
            }

            return spend;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}