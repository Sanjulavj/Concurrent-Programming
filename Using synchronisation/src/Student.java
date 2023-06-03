public class Student extends Thread {

    private Printer printer;

    public Student(String name, ThreadGroup group, Printer printer) {
        super(group, name);
        this.printer = printer;
    }


    @Override
    public void run(){
        int numOfDocs = 0;
        int totalNumPages = 0;
        for(int i = 1; i <= 5; i++){
            int minPages = 10;
            int maxPages = 25;
            //generate random number of pages value from 10 to 40
            int numOfPages = (int)(Math.random()*(maxPages-minPages+1)+minPages);

            Document doc = new Document(getName() , "Concurrent book - " + i , numOfPages);

            totalNumPages += doc.getNumberOfPages();
            numOfDocs += 1;

            ConsoleOutput.print(ConsoleOutput.DataFormat.STUDENT, "Printing the [" + doc.getDocumentName() + "] (pages - " + doc.getNumberOfPages() +") ] by [" + getName() +"]" , ConsoleOutput.MessageFormat.INFO);
            printer.printDocument(doc);

            int minSleep = 1000;
            int maxSleep = 5000;
            //generate random number of sleep time from 1000ms to 5000ms
            int sleepTime = (int)(Math.random()*(maxSleep-minSleep+1)+ minSleep);
            try{
                sleep(sleepTime);
            }catch (InterruptedException e ){
                ConsoleOutput.print(ConsoleOutput.DataFormat.STUDENT,e.toString() , ConsoleOutput.MessageFormat.ERROR);
            }

        }
        ConsoleOutput.print(ConsoleOutput.DataFormat.STUDENT, getName() + " finished printing: "+ numOfDocs + " documents, " + totalNumPages + " pages", ConsoleOutput.MessageFormat.INFO);
    }


}
