package ru.fintech.qa.homework.jdbc;

import org.junit.jupiter.api.*;
import ru.fintech.qa.homework.db.jdbc.DbService;
import ru.fintech.qa.homework.utils.BeforeUtils;

public class JDBCTests {

    private DbService dbService;

    @BeforeAll
    public static void beforeAll() {
        BeforeUtils.createData();
    }

    @BeforeEach
    public void beforeEach() {
        dbService = new DbService().establishNewConnection();
    }

    @AfterEach
    public void afterEach() {
        dbService.closeConnection();
    }

    @Test
    @DisplayName("Тест 1")
    public void rowsCountTest() {
        System.out.println("Тест 1. В таблице public.animal ровно 10 записей");
        int rowsCount = dbService.executeQueryCountRows("select * from public.animal");
        Assertions.assertEquals(10, rowsCount);
    }

    @Test
    @DisplayName("Тест 2")
    public void insertExistingIdTest() {
        System.out.println("Тест 2. В таблицу public.animal нельзя добавить строку с индексом от 1 до 10 включительно");
        System.out.println("Индекс 1:");
        int updatedRows = dbService.executeQueryUpdatedRows(
                "insert into public.animal (id, \"name\", age, \"type\", sex, place) values (1, 'Test_Name', 1, 1, 1, 1)");
        Assertions.assertEquals(0, updatedRows);
        System.out.println("Индекс 10:");
        updatedRows = dbService.executeQueryUpdatedRows(
                "insert into public.animal (id, \"name\", age, \"type\", sex, place) values (10, 'Test_Name', 1, 1, 1, 1)");
        Assertions.assertEquals(0, updatedRows);
        System.out.println("Индекс 11:");
        updatedRows = dbService.executeQueryUpdatedRows(
                "insert into public.animal (id, \"name\", age, \"type\", sex, place) values (11, 'Test_Name', 1, 1, 1, 1)");
        Assertions.assertEquals(1, updatedRows);
        dbService.executeQueryUpdatedRows("delete from public.animal where id = 11");
    }

    @Test
    @DisplayName("Тест 3")
    public void insertNullNameTest() {
        System.out.println("Тест 3. В таблицу public.workman нельзя добавить строку с name = null");
        int updatedRows = dbService.executeQueryUpdatedRows(
                "insert into public.workman (id, \"name\", age, \"position\") values (10, null, 1, 1)");
        Assertions.assertEquals(0, updatedRows);
    }

    @Test
    @DisplayName("Тест 4")
    public void oneMoreRowTest() {
        System.out.println("Тест 4. Если в таблицу public.places добавить еще одну строку, то в ней будет 6 строк");
        dbService.executeQueryUpdatedRows(
                "insert into public.places (id, \"row\", place_num, \"name\") values (100, 1, 1, 'Test_Place')");
        int rowsCount = dbService.executeQueryCountRows("select * from public.places");
        Assertions.assertEquals(6, rowsCount);
        dbService.executeQueryUpdatedRows("delete from public.places where id = 100");
    }

    @Test
    @DisplayName("Тест 5")
    public void zooRowsTest() {
        System.out.println("Тест 5. В таблице public.zoo всего три записи с name 'Центральный', 'Северный', 'Западный'");
        int rowsCount = dbService.executeQueryCountRows("select * from public.zoo");
        int centralZooCount = dbService.executeQueryCountRows(
                "select * from public.zoo where \"name\" = 'Центральный'");
        int northernZooCount = dbService.executeQueryCountRows(
                "select * from public.zoo where \"name\" = 'Северный'");
        int westernZooCount = dbService.executeQueryCountRows(
                "select * from public.zoo where \"name\" = 'Западный'");
        Assertions.assertEquals(3, rowsCount);
        Assertions.assertEquals(1, centralZooCount);
        Assertions.assertEquals(1, northernZooCount);
        Assertions.assertEquals(1, westernZooCount);
    }
}
