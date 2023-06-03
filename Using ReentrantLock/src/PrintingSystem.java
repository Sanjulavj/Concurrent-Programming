public class PrintingSystem {

    public static void main(String[] args) {
        ConsoleOutput.clearFile();

        ThreadGroup groupStudent = new ThreadGroup("Student group");
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student Thread Group Created!", ConsoleOutput.MessageFormat.INFO);
        ThreadGroup groupTechnician = new ThreadGroup("Technician group");
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Technician Thread Group Created!", ConsoleOutput.MessageFormat.INFO);

        ServicePrinter laserPrinter = new LaserPrinter( "01", groupStudent);
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Initialised the Printer!", ConsoleOutput.MessageFormat.INFO);

        Thread student1 = new Student("student1", groupStudent, laserPrinter);
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Initialised Student - " + student1.getName()+"!", ConsoleOutput.MessageFormat.INFO);
        Thread student2 = new Student( "student2",groupStudent, laserPrinter);
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Initialised Student - " + student2.getName()+"!", ConsoleOutput.MessageFormat.INFO);
        Thread student3 = new Student( "student3", groupStudent, laserPrinter);
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Initialised Student - " + student3.getName()+"!", ConsoleOutput.MessageFormat.INFO);
        Thread student4 = new Student("student4", groupStudent, laserPrinter);
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Initialised Student - " + student4.getName()+"!", ConsoleOutput.MessageFormat.INFO);


        Thread threadPaperTechnician = new PaperTechnician("Paper tech", groupTechnician, laserPrinter);
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Initialised Paper Technician!", ConsoleOutput.MessageFormat.INFO);
        Thread threadTonerTechnician = new TonerTechnician("Toner tech", groupTechnician, laserPrinter);
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Initialised Toner Technician!" , ConsoleOutput.MessageFormat.INFO);

        student1.start();
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student - " + student1.getName()+ " Thread Started!", ConsoleOutput.MessageFormat.INFO);
        student2.start();
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student - " + student2.getName()+ " Thread Started!", ConsoleOutput.MessageFormat.INFO);
        student3.start();
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student - " + student3.getName()+ " Thread Started!", ConsoleOutput.MessageFormat.INFO);
        student4.start();
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student - " + student4.getName()+ " Thread Started!", ConsoleOutput.MessageFormat.INFO);
        threadPaperTechnician.start();
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Paper Technician Thread Started!", ConsoleOutput.MessageFormat.INFO);
        threadTonerTechnician.start();
        ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Toner Technician Thread Started!", ConsoleOutput.MessageFormat.INFO);
        
        try {
            student1.join();
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student - " + student1.getName()+ " Completed Execution!", ConsoleOutput.MessageFormat.INFO);
            student2.join();
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student - " + student2.getName()+ " Completed Execution!", ConsoleOutput.MessageFormat.INFO);
            student3.join();
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student - " + student3.getName()+ " Completed Execution!", ConsoleOutput.MessageFormat.INFO);
            student4.join();
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Student - " + student4.getName()+ " Completed Execution!", ConsoleOutput.MessageFormat.INFO);
            threadPaperTechnician.join();
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Paper Technician Completed Execution!", ConsoleOutput.MessageFormat.INFO);
            threadTonerTechnician.join();
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Toner Technician Completed Execution!", ConsoleOutput.MessageFormat.INFO);
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "All tasks successfully completed!", ConsoleOutput.MessageFormat.INFO);
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, "Final Status of the printer - " + laserPrinter.toString(), ConsoleOutput.MessageFormat.INFO);
        } catch (InterruptedException e) {
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTING_SYSTEM, e.toString(), ConsoleOutput.MessageFormat.ERROR);
        }

    }
}
