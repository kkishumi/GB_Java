import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class PhoneBook {
    // Создаем HashMap для хранения телефонной книги
    private static HashMap<String, ArrayList<Integer>> phoneBook = new HashMap<>();

    public static void main(String[] args) {
        // Создаем окно
        JFrame frame = new JFrame("Телефонная книга");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Создаем выпадающее меню с опциями
        String[] options = {"Добавить номер", "Показать телефонную книгу", "Удалить номер", "Изменить номер"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        // Создаем текстовые поля для ввода имени и номера телефона
        JTextField nameField = new JTextField(10);
        JTextField phoneField = new JTextField(10);

        // Создаем кнопку для выполнения действий
        JButton actionButton = new JButton("Выполнить");

        // Обработчик выбора из JComboBox
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                if ("Показать телефонную книгу".equals(selectedOption)) {
                    showPhoneBook();
                } else if ("Добавить номер".equals(selectedOption)) {
                    actionButton.setText("Добавить");
                } else if ("Удалить номер".equals(selectedOption)) {
                    actionButton.setText("Удалить");
                } else if ("Изменить номер".equals(selectedOption)) {
                    actionButton.setText("Изменить");
                }
            }
        });

        // Обработчик нажатия на кнопку
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String selectedOption = (String) comboBox.getSelectedItem();

                if ("Добавить номер".equals(selectedOption)) {
                    int phoneNumber;
                    try {
                        phoneNumber = Integer.parseInt(phoneField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Пожалуйста, введите правильный номер телефона.");
                        return;
                    }
                    addContact(name, phoneNumber);
                    JOptionPane.showMessageDialog(frame, "Контакт успешно добавлен!");
                } else if ("Удалить номер".equals(selectedOption)) {
                    removeContact(name);
                    JOptionPane.showMessageDialog(frame, "Контакт успешно удален!");
                } else if ("Изменить номер".equals(selectedOption)) {
                    int phoneNumber;
                    try {
                        phoneNumber = Integer.parseInt(phoneField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Пожалуйста, введите правильный номер телефона.");
                        return;
                    }
                    updateContact(name, phoneNumber);
                    JOptionPane.showMessageDialog(frame, "Контакт успешно изменен!");
                }

                // Очищаем текстовые поля после добавления, удаления или изменения контакта
                nameField.setText("");
                phoneField.setText("");
            }
        });

        // Добавляем компоненты в окно
        frame.setLayout(new FlowLayout());
        frame.add(comboBox);
        frame.add(new JLabel("Имя:"));
        frame.add(nameField);
        frame.add(new JLabel("Телефон:"));
        frame.add(phoneField);
        frame.add(actionButton);

        // Делаем окно видимым
        frame.setVisible(true);
    }

    // Метод для добавления контакта
    public static void addContact(String name, int phoneNumber) {
        // Получаем список номеров для данного имени или создаем новый, если имя отсутствует
        ArrayList<Integer> phoneNumbers = phoneBook.getOrDefault(name, new ArrayList<>());
        // Добавляем номер телефона в список
        phoneNumbers.add(phoneNumber);
        // Сортируем список номеров по убыванию
        Collections.sort(phoneNumbers, Collections.reverseOrder());
        // Обновляем запись в телефонной книге
        phoneBook.put(name, phoneNumbers);
    }

    // Метод для удаления контакта
    public static void removeContact(String name) {
        // Удаляем запись из телефонной книги по имени
        phoneBook.remove(name);
    }

    // Метод для изменения контакта
    public static void updateContact(String name, int newPhoneNumber) {
        // Получаем список номеров для данного имени или создаем новый, если имя отсутствует
        ArrayList<Integer> phoneNumbers = phoneBook.getOrDefault(name, new ArrayList<>());
        // Если список номеров не пуст, заменяем первый номер на новый
        if (!phoneNumbers.isEmpty()) {
            phoneNumbers.set(0, newPhoneNumber);
        } else {
            // Если список пуст, добавляем новый номер
            phoneNumbers.add(newPhoneNumber);
        }
        // Сортируем список номеров по убыванию
        Collections.sort(phoneNumbers, Collections.reverseOrder());
        // Обновляем запись в телефонной книге
        phoneBook.put(name, phoneNumbers);
    }

    // Метод для отображения телефонной книги
    public static void showPhoneBook() {
        StringBuilder sb = new StringBuilder();
        // Проходим по всем записям в телефонной книге
        for (HashMap.Entry<String, ArrayList<Integer>> entry : phoneBook.entrySet()) {
            // Получаем список номеров для данного имени
            ArrayList<Integer> phoneNumbers = entry.getValue();
            // Сортируем список номеров по убыванию
            Collections.sort(phoneNumbers, Collections.reverseOrder());
            // Добавляем запись в строку
            sb.append(entry.getKey()).append(": ").append(phoneNumbers).append("\n");
        }
        // Отображаем содержимое телефонной книги в диалоговом окне
        JOptionPane.showMessageDialog(null, sb.toString(), "Телефонная книга", JOptionPane.INFORMATION_MESSAGE);
    }
}
