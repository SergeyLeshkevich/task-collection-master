package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        int zooNum = 3;
        int minAge = 9;
        int maxAge = 20;
        int limitAnimals = 7;
        int animalsInZoo = 7;
        animals.stream().filter(animal -> animal.getAge() > minAge && animal.getAge() < maxAge)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip((zooNum - 1) * animalsInZoo)
                .limit(limitAnimals)
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(an -> an.getOrigin().equals("Japanese"))
                .map(animal -> {
                    if (animal.getGender().equals("Female")) {
                        animal.setBread(animal.getBread().toUpperCase());
                    }
                    return animal.getBread();
                })
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        int minAgeAnimals = 30;
        animals.stream().filter(animal -> animal.getAge() > minAgeAnimals)
                .map(Animal::getOrigin)
                .filter(o -> o.charAt(0) == 'A')
                .distinct()
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().filter(animal -> animal.getGender().equals("Female"))
                .count());
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        int minAge = 20;
        int maxAge = 30;
        System.out.println(animals.stream().anyMatch(animal -> animal.getAge() >= minAge
                && animal.getAge() <= maxAge
                && animal.getOrigin().equals("Hungarian")));
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().allMatch(animal -> animal.getGender().equals("Male") || animal.getGender().equals("Female")));
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().noneMatch(animal -> animal.getOrigin().equals("Oceania")));
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        int limitAnimals = 100;
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(limitAnimals)
                .max(Comparator.comparingInt(Animal::getAge))
                .ifPresentOrElse(animal -> {
                            System.out.println(animal.getAge());
                        },
                        () -> {
                            System.out.println("animals is present");
                        });
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .map(chars -> chars.length)
                .min(Integer::compareTo).orElse(-1));
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .map(Animal::getAge)
                .reduce(Integer::sum).orElse(-1));
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        System.out.print(animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .collect(Collectors.averagingDouble(Animal::getAge)));
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        int minAge = 18;
        int maxAge = 27;
        int limitPeople = 200;

        persons.stream()
                .filter(person -> {
                    LocalDate currentDate = LocalDate.now();
                    double age = Period.between(person.getDateOfBirth(), currentDate).getYears();
                    return person.getGender().equals("Male")
                            && age >= minAge
                            && age < maxAge;
                })
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(limitPeople)
                .forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        int minAge = 18;
        int retirementAge = 60;
        int limitPeople = 500;

        List<Person> people = new ArrayList<>(houses.stream()
                .map(house -> {
                    List<Person> personList = new ArrayList<>();
                    if (house.getBuildingType().equals("Hospital")) {
                        personList.addAll(house.getPersonList());
                    }
                    return personList;
                })
                .flatMap(Collection::stream)
                .toList());
        people.addAll(
                houses.stream()
                        .filter(house -> !house.getBuildingType().equals("Hospital"))
                        .flatMap(house -> house.getPersonList().stream())
                        .sorted(Comparator.comparing(person -> {
                            LocalDate currentDate = LocalDate.now();
                            double age = Period.between(person.getDateOfBirth(), currentDate).getYears();
                            return !(age < minAge || age > retirementAge);
                        }))
                        .limit(limitPeople - people.size())
                        .toList()
        );
        System.out.println(people);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();

        System.out.println(cars.stream()
                .map(Main::getCarEntry)
                .filter(entry -> entry.getKey() < 7)
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summarizingDouble(entry -> entry.getValue().getMass())))
                .entrySet().stream()
                .peek(entry -> {
                    System.out.println("Эшелон №" + entry.getKey() + ": " + entry.getValue().getSum() / 1000 * 7.14);
                })
                .mapToDouble(entry -> entry.getValue().getSum() / 1000 * 7.14)
                .sum());
//вариант 2
//        cars.stream()
//                .map(Main::getCarEntry)
//                .filter(entry -> entry.getKey() < 7)
//                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue,
//                        Collectors.toList())))
//                .values().stream()
//                .map(value -> value.stream()
//                        .mapToDouble(Car::getMass)
//                        .sum() * 7.14 / 1000)
//                .peek(System.out::println)
//                .mapToDouble(price -> price)
//                .sum();
    }

    private static Map.Entry<Integer, Car> getCarEntry(Car car) {
        int key = 7;
        if (car.getCarModel().equals("Jaguar")
                || car.getColor().equals("While")) {
            key = 1;
        } else if (car.getMass() < 1500
                || car.getCarModel().equals("BMW")
                || car.getCarModel().equals("Lexus")
                || car.getCarModel().equals("Toyota")
                || car.getCarModel().equals("Chrysler")) {
            key = 2;
        } else if (car.getColor().equals("Black")
                && car.getMass() > 4000 || car.getCarModel().equals("GMC")
                || car.getCarModel().equals("Dodge")) {
            key = 3;
        } else if (car.getReleaseYear() < 1982
                || car.getCarModel().equals("Civic")
                || car.getCarModel().equals("Cherokee")) {
            key = 4;
        } else if (!(car.getColor().equals("Yellow")
                || car.getColor().equals("Red")
                || car.getColor().equals("Green")
                || car.getColor().equals("Blue"))
                || car.getPrice() > 40000) {
            key = 5;
        } else if (car.getVin().contains("59")) {
            key = 6;
        }
        return Map.entry(key, car);
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        int periodInYears = 5;
        double priceWater = 1.39;

        double sum = flowers.stream().sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay))
                .filter(flower -> {
                    char capitalLetterOfName = flower.getCommonName().charAt(0);
                    return capitalLetterOfName <= 'S'
                            && capitalLetterOfName >= 'C';
                })
                .filter(flower -> {
                    return flower.isShadePreferred()
                            && flower.getFlowerVaseMaterial().stream()
                            .filter(material -> material.equals("Steel")
                                    || material.equals("Aluminum")
                                    || material.equals("Glass")).toList().size() > 0;
                }).mapToDouble(flower -> {
                    LocalDate startDate = LocalDate.now();
                    LocalDate endDate = startDate.plusYears(periodInYears);
                    long days = ChronoUnit.DAYS.between(startDate, endDate);
                    return flower.getPrice() + (flower.getWaterConsumptionPerDay() / 1000 * days * priceWater);
                }).sum();
        System.out.println("Сумма к оплате: " + sum);
    }

    public static void task16() {
        int minAge = 18;
        List<Student> students = Util.getStudents();

        students.stream().filter(student -> student.getAge() < minAge)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(System.out::println);
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream().map(Student::getGroup)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors
                        .groupingBy(Student::getFaculty, Collectors.averagingDouble(Student::getAge)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(System.out::println);
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        int minGrade = 4;
        String group = "C-3";

        students.stream().filter(student -> {
            List<Examination> examinationList = examinations.stream()
                    .filter(exs -> exs.getStudentId() == student.getId() && student.getGroup().equals(group))
                    .toList();
            Examination examination;
            if (examinationList.isEmpty()) {
                return false;
            } else {
                examination = examinationList.get(0);
                return examination.getExam3() > minGrade;
            }
        }).forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();

        Map<String, Double> facultyAverageExam1 = examinations.stream()
                .collect(Collectors.groupingBy(e -> students.stream()
                                .filter(s -> s.getId() == e.getStudentId())
                                .findFirst()
                                .orElse(new Student())
                                .getFaculty(),
                        Collectors.averagingDouble(Examination::getExam1)));

        Optional<Map.Entry<String, Double>> maxAverage = facultyAverageExam1.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        if (maxAverage.isPresent()) {
            System.out.print("Faculty with the highest average score on the first exam:"
                    + maxAverage.get().getKey());
        } else {
            System.out.println("No data.");
        }

        //вариант2
//        students.stream()
//                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.averagingDouble(
//                        student -> examinations.stream()
//                                .filter(examination -> examination.getStudentId() == student.getId())
//                                .mapToDouble(Examination::getExam1)
//                                .findFirst()
//                                .orElse(0.0)
//                )))
//                .entrySet().stream()
//                .max(Map.Entry.comparingByValue())
//                .stream().collect(Collectors.toSet()).forEach(System.out::println);

    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        students.stream().collect(Collectors.groupingBy(Student::getGroup, Collectors.summarizingInt(Student::getId)))
                .forEach((k, v) -> System.out.println(k + ": " + v.getCount()));
//  вариант2
//        students.stream().collect(Collectors.groupingBy(Student::getGroup))
//                .forEach((k,v)-> System.out.println(k + ": " + v.size()));
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        students.stream().collect(Collectors.groupingBy(Student::getFaculty, Collectors.summarizingInt(Student::getAge)))
                .forEach((k, v) -> System.out.println(k + ": " + v.getMin()));
    }
}
