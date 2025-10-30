class Printer {  
    private boolean isPrinting = false; 
       synchronized void printDocument(String user, String documentName) {
        while (isPrinting) { 
 {
                System.out.println(user + " is waiting for the printer...");
                wait(); 
            } catch (InterruptedException e) {
                System.out.println(user + " interrupted while waiting.");
            }
        }

       
        isPrinting = true;
        System.out.println(user + " is printing: " + documentName);

        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            System.out.println("Printing interrupted for " + user);
        }

        System.out.println(user + " finished printing: " + documentName);
        isPrinting = false;
        notify(); 
    }
}

class User implements Runnable {
    private String userName;
    private Printer printer;
    private boolean running = true;

    User(String name, Printer printer) {
        this.userName = name;
        this.printer = printer;
    }

    @Override
    public void run() {
        int docCount = 1;
        while (running && docCount <= 3) { 
            String docName = "Document_" + docCount;
            printer.printDocument(userName, docName);
            docCount++;

            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {}
        }
        System.out.println(userName + " has completed all print jobs and stopped safely.");
    }

    public void stopUser() {
        running = false; 
    }
}

public class PrinterSimulation {
    public static void main(String[] args) throws InterruptedException {
        Printer sharedPrinter = new Printer();

        Thread user1 = new Thread(new User("User1", sharedPrinter));
        Thread user2 = new Thread(new User("User2", sharedPrinter));
        Thread user3 = new Thread(new User("User3", sharedPrinter));

     
        System.out.println("Before start: " + user1.getName() + " state = " + user1.getState());

        user1.start();
        user2.start();
        user3.start();

        Thread.sleep(2000);

        
        System.out.println("\nDuring execution:");
        System.out.println(user1.getName() + " state = " + user1.getState());
        System.out.println(user2.getName() + " state = " + user2.getState());
        System.out.println(user3.getName() + " state = " + user3.getState());

        
        user1.join();
        user2.join();
        user3.join();

        System.out.println("\nAfter completion:");
        System.out.println(user1.getName() + " state = " + user1.getState());
        System.out.println(user2.getName() + " state = " + user2.getState());
        System.out.println(user3.getName() + " state = " + user3.getState());

        System.out.println("\nMain thread exiting...");
    }
}