package org.example;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
       main.task1();
       main.task2();
       main.task3();
    }

    private void task1() {
        List<Integer> rand = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            rand.add((int) (-100 + Math.random() * 200));
        }
        System.out.println("Изначальный список: " + rand);
        long evenCount = rand.stream().filter(i -> i % 2 == 0).count();
        System.out.println("Количество четных чисел: " + evenCount);
        long oddCount = rand.stream().filter(i -> i % 2 != 0).count();
        System.out.println("Количество нечетных чисел: " + oddCount);
        long nullCount = rand.stream().filter(i -> i == 0).count();
        System.out.println("Количество нулей: " + nullCount);
    }

    private void task2() {
        Random random = new Random();
        List<City> cityList = new ArrayList<>();
        City lastCity = new City();
        for (int i = 0; i < 20; i++) {
            if (random.nextDouble() < 0.5) {
                // С вероятностью 50% берем предыдущий город
                lastCity = new City();
            }
            cityList.add(lastCity);
        }
        Collections.shuffle(cityList); // Перемешиваем список

        // Выводим все города
        System.out.println("Список всех городов:");
        cityList.forEach(System.out::println);
        System.out.println("----------------------");

        // Выводим частоту встречаемости каждого города
        System.out.println("Частота встречаемости городов");
        cityList.stream()
                .collect(Collectors.groupingBy(
                        city -> city.name.toLowerCase(),
                        Collectors.counting()
                ))
                .forEach((name, count) ->
                        System.out.println(name + ": " + count + " раз(а)"));
        System.out.println();

        // Запрашиваем у пользователя город для поиска
        System.out.println("Введите название города: ");
        Scanner scanner = new Scanner(System.in);
        String searchCity = scanner.nextLine();

        // Считаем количество вхождений (без учета регистра)
        long count = cityList.stream()
                .filter(city -> city.name.equalsIgnoreCase(searchCity))
                .count();

        System.out.printf("Город '%s' встречается %d раз(а)%n", searchCity, count);
        scanner.close();
    }

    private static class City {
        String name;
        String country;

        public City() {
            Faker faker = new Faker();
            this.name = faker.address().city();
            this.country = faker.address().country();
        }

        @Override
        public String toString() {
            return name + "(" + country + ")";
        }

        // Переопределяем equals и hashCode для корректной работы коллекций
        @Override
        public boolean equals(Object anotherCity) {
            if (this == anotherCity) return true;
            if (anotherCity == null || getClass() != anotherCity.getClass()) return false;
            City city = (City) anotherCity;

            return name.equalsIgnoreCase(city.name); // сравнение без учета регистра
        }

        @Override
        public int hashCode() {
            return Objects.hash(name.toLowerCase()); // hashCode без учета регистра
        }
    }

    private void task3() {
        // Создайте класс "Автомобиля". Он должен хранить информацию о названии автомобиля, год выпуска, цена, цвет,
        // объём двигателя. Нужно создать набор автомобилей и выполнить следующие задачи:
        // * Показать все автомобили;
        // * Показать все автомобили заданного цвета;
        // * Показать все автомобили заданного объёма;
        // * Показать все автомобили дороже заданной цены;
        // * Показать все автомобили чей год выпуска находится в указанном диапазоне;
        List<Car> cars = Arrays.asList(
                new Car("Toyota Camry", 2020, 25000, "Черный", 2.5),
                new Car("Honda Accord", 2019, 23000, "Синий", 2.0),
                new Car("BMW X5", 2022, 60000, "Белый", 3.0),
                new Car("Audi A6", 2021, 55000, "Черный", 2.8)
        );

        System.out.println("Все автомобили:");
        cars.forEach(System.out::println);

        System.out.println("\nАвтомобили черного цвета:");
        filterByColor(cars, "Черный").forEach(System.out::println);

        System.out.println("\nАвтомобили с объемом двигателя 2.5:");
        filterByEngineVolume(cars, 2.5).forEach(System.out::println);

        System.out.println("\nАвтомобили дороже 25000:");
        filterByPrice(cars, 25000).forEach(System.out::println);

        System.out.println("\nАвтомобили 2020-2022 годов:");
        filterByYearRange(cars, 2020, 2022).forEach(System.out::println);
    }

    // Фильтр по цвету
    private static List<Car> filterByColor(List<Car> cars, String color) {
        return cars.stream()
                .filter(car -> car.color.equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    // Фильтр по объему двигателя
    private static List<Car> filterByEngineVolume(List<Car> cars, double volume) {
        return cars.stream()
                .filter(car -> car.engineVolume == volume)
                .collect(Collectors.toList());
    }

    // Фильтр по цене (дороже указанной)
    private static List<Car> filterByPrice(List<Car> cars, double minPrice) {
        return cars.stream()
                .filter(car -> car.price > minPrice)
                .collect(Collectors.toList());
    }

    // Фильтр по диапазону годов
    private static List<Car> filterByYearRange(List<Car> cars, int minYear, int maxYear) {
        return cars.stream()
                .filter(car -> car.year >= minYear && car.year <= maxYear)
                .collect(Collectors.toList());
    }
}

class Car {
    String name;
    int year;
    double price;
    String color;
    double engineVolume;

    public Car(String name, int year, double price, String color, double engineVolume) {
        this.name = name;
        this.year = year;
        this.price = price;
        this.color = color;
        this.engineVolume = engineVolume;
    }

    @Override
    public String toString() {
        return name + " (" + year + ", " + color + ", " + engineVolume + "L, $" + price + ")";
    }

}