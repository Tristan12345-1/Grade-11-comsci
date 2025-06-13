// Name: Tristan Padilla

// Import necessary classes
import java.util.Scanner;       // For reading user input
import java.util.Arrays;        // (Not used here, but often useful for array utilities)
import java.util.ArrayList;     // To use dynamic lists for storing services and bookings

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // Scanner to read input from user

        // Lists to store service details
        ArrayList<String> serviceNames = new ArrayList<>();     // List of service names
        ArrayList<String> categories = new ArrayList<>();       // List of service categories (e.g., cleaning, tutoring)
        ArrayList<Double> rates = new ArrayList<>();            // List of hourly rates for services
        ArrayList<Integer> availabilities = new ArrayList<>();  // List of hours available for each service

        // Lists to store the user's cart (services they want to book)
        ArrayList<String> cartServices = new ArrayList<>();     // Names of services in the cart
        ArrayList<Integer> cartHours = new ArrayList<>();       // Hours booked for each service in the cart

        // Infinite loop for menu interaction until user chooses to exit
        while (true) {
            // Display the main menu
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add a Service");
            System.out.println("2. Book a Service");
            System.out.println("3. View Cart and Checkout");
            System.out.println("4. Exit");
            System.out.println("Choose an option: ");
            String choice = sc.nextLine();  // Read user's choice

            // Option 1: Add a service to the system
            if (choice.equals("1")) {
                System.out.println("Enter Service Name: ");
                String name = sc.nextLine();

                System.out.println("Enter Category: ");
                String category = sc.nextLine();

                System.out.println("Enter Hourly Rate: ");
                double rate = sc.nextDouble(); // Read rate as double

                System.out.println("Enter Available Hours: ");
                int available = sc.nextInt(); // Read available hours as int
                sc.nextLine(); // Consume the newline character left after nextInt()

                // Store all service information in their respective lists
                serviceNames.add(name);
                categories.add(category);
                rates.add(rate);
                availabilities.add(available);

                System.out.println("Service " + name + " added successfully.");

            } else if (choice.equals("2")) {
                // Option 2: Book a service
                if (serviceNames.isEmpty()) {
                    System.out.println("No services available."); // Nothing to book
                } else {
                    // Show available categories (may include duplicates)
                    ArrayList<String> uniqueCategories = new ArrayList<>(categories); // Could be improved to only show unique
                    System.out.println("Available Categories:");
                    for (String cat : uniqueCategories) {
                        System.out.println("- " + cat); // List each category
                    }

                    System.out.println("Choose a category: ");
                    String selectedCategory = sc.nextLine();

                    // Find all services in the chosen category
                    ArrayList<Integer> matchedIndexes = new ArrayList<>();
                    System.out.println("Services in category: " + selectedCategory);

                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).equalsIgnoreCase(selectedCategory)) {
                            matchedIndexes.add(i); // Store index of matching service

                            // Check if service is available
                            String status = "";
                            if (availabilities.get(i) > 0) {
                                status = availabilities.get(i) + " hour(s) available";
                            } else {
                                status = "Fully Booked";
                            }

                            // Display service details
                            System.out.println((matchedIndexes.size()) + ". " + serviceNames.get(i)
                                    + " - $" + rates.get(i) + "/hr (" + status + ")");
                        }
                    }

                    if (matchedIndexes.isEmpty()) {
                        System.out.println("No services found in that category.");
                    } else {
                        System.out.println("Select a service by number: ");
                        int selected = sc.nextInt() - 1;
                        sc.nextLine(); // consume leftover newline

                        // Validate selection
                        if (selected < 0 || selected >= matchedIndexes.size()) {
                            System.out.println("Invalid selection.");
                        } else {
                            int serviceIndex = matchedIndexes.get(selected);

                            // Check if it's fully booked
                            if (availabilities.get(serviceIndex) == 0) {
                                System.out.println("This service is fully booked.");
                            } else {
                                System.out.println("Enter number of hours to book: ");
                                int hoursToBook = sc.nextInt();
                                sc.nextLine(); // clear newline

                                // Check for invalid or over-limit bookings
                                if (hoursToBook > availabilities.get(serviceIndex)) {
                                    System.out.println("Only " + availabilities.get(serviceIndex) + " hour(s) available.");
                                } else if (hoursToBook <= 0) {
                                    System.out.println("You must book at least 1 hour.");
                                } else {
                                    // Add to cart and update availability
                                    cartServices.add(serviceNames.get(serviceIndex));
                                    cartHours.add(hoursToBook);

                                    int newAvailability = availabilities.get(serviceIndex) - hoursToBook;
                                    availabilities.set(serviceIndex, newAvailability);

                                    System.out.println("Added to cart.");
                                }
                            }
                        }
                    }
                }

            } else if (choice.equals("3")) {
                // Option 3: View cart and proceed to checkout
                if (cartServices.isEmpty()) {
                    System.out.println("Your cart is empty.");
                } else {
                    double total = 0;
                    System.out.println("\n--- Invoice ---");

                    // Loop through cart and calculate total cost
                    for (int i = 0; i < cartServices.size(); i++) {
                        String name = cartServices.get(i);
                        int hours = cartHours.get(i);
                        int index = serviceNames.indexOf(name); // Find index to get rate
                        double cost = rates.get(index) * hours;
                        total += cost;

                        System.out.println(name + " - " + hours + " hour(s) - $" + cost);
                    }

                    System.out.println("Total: $" + total);

                    // Clear cart after checkout
                    cartServices.clear();
                    cartHours.clear();

                    System.out.println("Thank you for your order!");
                }

            } else if (choice.equals("4")) {
                // Option 4: Exit the program
                System.out.println("Goodbye!");
                break;

            } else {
                // If user enters an invalid option
                System.out.println("Invalid option. Try again.");
            }
        }

        // Close the scanner when the program ends
        sc.close();
    }
}