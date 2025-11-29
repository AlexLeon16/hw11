package ru.netology.stats;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TodosTest {

    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchTasks() {
        SimpleTask simpleTask = new SimpleTask(5, "Купить Хлеб");
        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
        Epic epic = new Epic(55, subtasks);
        Meeting meeting = new Meeting(555, "Обсуждение Хлеб", "Проект Хлеб", "Завтра");

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        // Поиск по слову "Хлеб" - должны найти все три задачи
        Task[] result = todos.search("Хлеб");
        Assertions.assertEquals(3, result.length);

        // Проверяем, что все найденные задачи содержат искомое слово
        for (Task task : result) {
            Assertions.assertTrue(task.matches("Хлеб"));
        }
    }

    @Test
    public void shouldSearchWhenNoMatches() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");
        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
        Epic epic = new Epic(55, subtasks);
        Meeting meeting = new Meeting(555, "Выкатка приложения", "НетоБанк", "Завтра");

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] result = todos.search("Сыр");
        Assertions.assertEquals(0, result.length);
    }

    @Test
    public void shouldSearchEmptyTodos() {
        Todos todos = new Todos();
        Task[] result = todos.search("что-то");
        Assertions.assertEquals(0, result.length);
    }

    @Test
    public void shouldSearchCaseSensitive() {
        SimpleTask simpleTask = new SimpleTask(5, "Купить хлеб");
        String[] subtasks = { "Молоко", "Яйца", "хлеб" };
        Epic epic = new Epic(55, subtasks);

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(epic);

        // Поиск с разным регистром
        Task[] result1 = todos.search("хлеб");
        Task[] result2 = todos.search("Хлеб");

        Assertions.assertEquals(2, result1.length); // найдет оба
        Assertions.assertEquals(0, result2.length); // не найдет ни одного
    }
}