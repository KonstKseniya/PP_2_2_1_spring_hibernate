package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User user1 = new User("Harry", "Potter", "potter@hogwarts.com");
      User user2 = new User("Hermione", "Granger", "granger@hogwarts.com");
      User user3 = new User("Ron", "Weasley", "weasley@hogwarts.com");
      User user4 = new User("Remus", "Lupin", "lupin@hogwarts.com");

      Car car1 = new Car(2021, "Lightning");
      Car car2 = new Car(1001, "Nimbus");
      Car car3 = new Car(7, "Cleanliness");
      Car car4 = new Car(290, "Comet");

      user1.addCar(car1);
      user2.addCar(car2);
      user3.addCar(car3);
      user4.addCar(car4);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);
      userService.add(user4);

      // 1. Пользователи с машинами
      System.out.println("1. _____________________________________________");
      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println("Car = " + user.getCar().getModel() + " " + user.getCar().getSeries());
         System.out.println();
      }

      // 2. Выбрать пользователя, владеющего машиной (по ее модели и серии)
      System.out.println("2. _____________________________________________");
      System.out.println("\nOwner car: "
              + userService.getUserByCar(1001, "Nimbus"));

      // Нет пользователя с такой машиной
      System.out.println("3. _____________________________________________");
      try {
         User notFoundUser = userService.getUserByCar(90, "Broom");
      } catch (NoResultException e) {
         System.out.println("User not found");
      }

      context.close();
   }
}
