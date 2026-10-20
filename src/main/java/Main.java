

import com.hallmanagement.dao.MenuDAOImpl;
import com.hallmanagement.model.MenuItem;
import com.hallmanagement.model.MealPrice;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MenuDAOImpl menuDAO = new MenuDAOImpl();
        
        System.out.println("--- Weekly Menu Test ---");
        List<MenuItem> menu = menuDAO.getWeeklyMenu();
        for (MenuItem item : menu) {
            System.out.println(item.getDayName() + " -> Breakfast: " + item.getBreakfastItem() + 
                               ", Lunch: " + item.getLunchItem() + ", Dinner: " + item.getDinnerItem());
        }
        
        System.out.println("\n--- Meal Prices Test ---");
        MealPrice price = menuDAO.getMealPrices();
        System.out.println("Breakfast Price: " + price.getBreakfastPrice());
        System.out.println("Lunch Price: " + price.getLunchPrice());
        System.out.println("Dinner Price: " + price.getDinnerPrice());
    }
}