// Name: Tristan Padilla
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Lists to store services and their details
        ArrayList<String> serviceNames = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> rates = new ArrayList<>();
        ArrayList<Integer> availabilities = new ArrayList<>();

        // Lists to store the user's cart
        ArrayList<String> cartServices = new ArrayList<>();
        ArrayList<Integer> cartHours = new ArrayList<>();

        while (true) {
            // Display main menu
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add a Service");
            System.out.println("2. Book a Service");
            System.out.println("3. View Cart and Checkout");
            System.out.println("4. Exit");
            System.out.println("Choose an option: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                // Add a service
                System.out.println("Enter Service Name: ");
                String name = sc.nextLine();
                System.out.println("Enter Category: ");
                String category = sc.nextLine();
                System.out.println("Enter Hourly Rate: ");
                double rate = sc.nextDouble();
                System.out.println("Enter Available Hours: ");
                int available = sc.nextInt();
                sc.nextLine(); // Clear newline

                serviceNames.add(name);
                categories.add(category);
                rates.add(rate);
                availabilities.add(available);

                System.out.println("Service " + name + " added successfully.");

            } else if (choice.equals("2")) {
                // Book a service
                if (serviceNames.size() == 0) {
                    System.out.println("No services available.");
                } else {
                    // Show all categories
                    System.out.println("Available Categories:");
                    ArrayList<String> shownCategories = new ArrayList<>();
                    for (String cat : categories) {
                        boolean alreadyShown = false;
                        for (String shown : shownCategories) {
                            if (cat.compareToIgnoreCase(shown) == 0) {
                                alreadyShown = true;
                                break;
                            }
                        }
                        if (!alreadyShown) {
                            shownCategories.add(cat);
                            System.out.println("- " + cat);
                        }
                    }

                    System.out.println("Choose a category: ");
                    String selectedCategory = sc.nextLine();

                    ArrayList<Integer> matchedIndexes = new ArrayList<>();
                    int serviceNumber = 1;
                    for (int i = 0; i < categories.size(); i++) {
                        String currentCategory = categories.get(i);
                        if (currentCategory.compareToIgnoreCase(selectedCategory) == 0) {
                            matchedIndexes.add(i);

                            String serviceStatus = "";
                            if (availabilities.get(i) > 0) {
                                serviceStatus = availabilities.get(i) + " hour(s) available";
                            } else {
                                serviceStatus = "Fully Booked";
                            }

                            String name = serviceNames.get(i);
                            double rate = rates.get(i);
                            System.out.println(serviceNumber + ". " + name + " - $" + rate + "/hr (" + serviceStatus + ")");
                            serviceNumber++;
                        }
                    }

                    if (matchedIndexes.size() == 0) {
                        System.out.println("No services found in that category.");
                    } else {
                        System.out.println("Select a service by number: ");
                        String selectedStr = sc.nextLine();
                        int selected = 0;

                        // Convert String input to int manually without using Integer.parseInt
                        for (int i = 0; i < selectedStr.length(); i++) {
                            char c = selectedStr.charAt(i);
                            if (c >= '0' && c <= '9') {
                                selected = selected * 10 + (c - '0');
                            } else {
                                selected = -1;
                                break;
                            }
                        }

                        selected -= 1; // adjust for index
                        if (selected < 0 || selected >= matchedIndexes.size()) {
                            System.out.println("Invalid selection.");
                        } else {
                            int realIndex = matchedIndexes.get(selected);

                            if (availabilities.get(realIndex) == 0) {
                                System.out.println("This service is fully booked.");
                            } else {
                                System.out.println("Enter number of hours to book: ");
                                String hoursStr = sc.nextLine();
                                int hoursToBook = 0;
                                for (int i = 0; i < hoursStr.length(); i++) {
                                    char c = hoursStr.charAt(i);
                                    if (c >= '0' && c <= '9') {
                                        hoursToBook = hoursToBook * 10 + (c - '0');
                                    } else {
                                        hoursToBook = -1;
                                        break;
                                    }
                                }

                                if (hoursToBook > availabilities.get(realIndex)) {
                                    System.out.println("Only " + availabilities.get(realIndex) + " hour(s) available.");
                                } else if (hoursToBook <= 0) {
                                    System.out.println("You must book at least 1 hour.");
                                } else {
                                    // Add to cart
                                    cartServices.add(serviceNames.get(realIndex));
                                    cartHours.add(hoursToBook);
                                    availabilities.set(realIndex, availabilities.get(realIndex) - hoursToBook);
                                    System.out.println("Added to cart.");
                                }
                            }
                        }
                    }
                }

            } else if (choice.equals("3")) {
                // View cart and checkout
                if (cartServices.size() == 0) {
                    System.out.println("Your cart is empty.");
                } else {
                    double total = 0;
                    System.out.println("\n--- Invoice ---");
                    for (int i = 0; i < cartServices.size(); i++) {
                        String name = cartServices.get(i);
                        int hours = cartHours.get(i);

                        int index = -1;
                        for (int j = 0; j < serviceNames.size(); j++) {
                            if (serviceNames.get(j).equals(name)) {
                                index = j;
                                break;
                            }
                        }

                        if (index != -1) {
                            double cost = rates.get(index) * hours;
                            total += cost;
                            System.out.println(name + " - " + hours + " hour(s) - $" + cost);
                        }
                    }

                    System.out.println("Total: $" + total);
                    cartServices.clear();
                    cartHours.clear();
                    System.out.println("Thank you for your order!");
                }

            } else if (choice.equals("4")) {
                System.out.println("Goodbye!");
                break;

            } else {
                System.out.println("Invalid option. Try again.");
            }
        }

        sc.close();
    }
}
