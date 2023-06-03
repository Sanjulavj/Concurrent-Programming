import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LaserPrinter implements ServicePrinter{

    private String id;
    private int currPaperLevel;
    private int currTonerLevel;
    private int numOfDocumentsPrinted;
    private ThreadGroup students;

    public static int paperRefill = 0;
    public static int tonerRefill = 0;

    // Reentrant lock with fairness enabled
    private Lock reentrantLocklock = new ReentrantLock(true);
    private Condition reentrantCondition = reentrantLocklock.newCondition();


    public LaserPrinter(String id, ThreadGroup students) {
        this.id = id;
        this.currPaperLevel = Full_Paper_Tray;
        //this.currTonerLevel = Full_Toner_Level;
        this.currTonerLevel = Full_Toner_Level - 470; //To check the all the functionalities in the spec
        this.numOfDocumentsPrinted = 0;
        this.students = students;
    }

    /**
     * printDocument to print documents for students if there is sufficient paper and toner to print the student's document or
     * to wait until technicians to replace papers and cartridges
     *
     * @param document to initiate the document to be printed
     */
    @Override
    public void printDocument(Document document) {
        reentrantLocklock.lock();
        int numOfDocumentPages = document.getNumberOfPages();
        try{
            //check papers levels and toner levels
            while (numOfDocumentPages >= currPaperLevel || numOfDocumentPages  >= currTonerLevel){
                if(numOfDocumentPages >= currPaperLevel && numOfDocumentPages >=  currTonerLevel ) {
                    ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER, "Papers and toners are not enough to print the document...." + " [Current paper level : " + currPaperLevel + "] [Current toner level : " + currTonerLevel+ "]", ConsoleOutput.MessageFormat.WARN);
                }
                else if(numOfDocumentPages >= currPaperLevel) {
                    ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Papers are not enough to print the document...." + " [Current paper level : " + currPaperLevel+ "]" , ConsoleOutput.MessageFormat.WARN);
                }
                else if(numOfDocumentPages >= currTonerLevel) {
                    ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Toners are not enough to print the document...." + " [Current toner level : " + currTonerLevel + "]" , ConsoleOutput.MessageFormat.WARN);
                }
                reentrantCondition.await(); // Resources are not up to the level. Wait till it's restored
            }

            //print papers if the resources are up to the level
            if(numOfDocumentPages < currPaperLevel && numOfDocumentPages <  currTonerLevel ) {
                ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER, "Documents are Printing.... " + this.toString(), ConsoleOutput.MessageFormat.INFO);
                this.currPaperLevel -= numOfDocumentPages;
                this.currTonerLevel -= numOfDocumentPages;
                this.numOfDocumentsPrinted++;
            }
            reentrantCondition.signalAll();
        }catch (InterruptedException e){
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,e.toString() , ConsoleOutput.MessageFormat.ERROR);
        }finally {
            reentrantLocklock.unlock();
        }

    }

    /**
     * replaceTonerCartridge to replace the toner cartridges.
     * toner cartridges can be replaced when it goes below the minimum level,
     * when toner level is replaced, it goes up to the full toner level
     */
    @Override
    public void replaceTonerCartridge() {
        reentrantLocklock.lock();
        try{
            while (checkTonersLimit()) {
                //check if all students have not finished the printing
                if (!printContinuous()) {
                    ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Students are still printing....[Toner replacing is waiting]", ConsoleOutput.MessageFormat.INFO);
                    reentrantCondition.await(5, TimeUnit.SECONDS);
                } else {
                    ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Students are not printing....[Toner replacing is not waiting]", ConsoleOutput.MessageFormat.INFO);
                    break;
                }
            }
            //check toner level
            if(!checkTonersLimit()){
                ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Toner level is low", ConsoleOutput.MessageFormat.WARN);
                ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Toner Replacing.... [ Added Toner Cartridge - "+ PagesPerTonerCartridge+" ] " + this.toString(), ConsoleOutput.MessageFormat.INFO);
                currTonerLevel += PagesPerTonerCartridge;
                tonerRefill += 1;
            }
            reentrantCondition.signalAll();
        }catch (InterruptedException e){
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,e.toString() , ConsoleOutput.MessageFormat.ERROR);
        }finally {
            reentrantLocklock.unlock();
        }

    }

    /**
     * refillPaper to refill paper tray with papers.
     * papers can be replaced when it goes below the minimum level,
     * when paper level is replaced, it replaces with a paper sheet pack
     */
    @Override
    public void refillPaper() {
        reentrantLocklock.lock();
        try{
            while (checkPapersLimit()){
                //check if all students have not finished the printing
                if(!printContinuous()) {
                    ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Students are still printing....[Paper replacing is waiting]", ConsoleOutput.MessageFormat.INFO);
                    reentrantCondition.await(5, TimeUnit.SECONDS);
                }else{
                    ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Students are not printing....[Paper replacing is not waiting]", ConsoleOutput.MessageFormat.INFO);
                    break;
                }

            }
            //check paper level
            if(!checkPapersLimit()){
                ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Paper level is low", ConsoleOutput.MessageFormat.WARN);
                ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,"Paper Replacing.... [ Added papers - "+ ServicePrinter.SheetsPerPack+" ] " + this.toString(), ConsoleOutput.MessageFormat.INFO);
                currPaperLevel += SheetsPerPack;
                paperRefill += 1;
            }
            reentrantCondition.signalAll();

        }catch (InterruptedException e){
            ConsoleOutput.print(ConsoleOutput.DataFormat.PRINTER,e.toString() , ConsoleOutput.MessageFormat.ERROR);
        }finally {
            reentrantLocklock.unlock();
        }
    }

    /**
     * checkPapersLimit to check the papers limit.
     *
     * @return true if paper limit is up to the limit, false otherwise.
     */
    private boolean checkPapersLimit() {
        return currPaperLevel + SheetsPerPack > Full_Paper_Tray;
    }

    /**
     * printContinuous to check the papers limit.
     *
     * @return true if all students have finished the printing, false otherwise.
     */
    private boolean printContinuous() {
        return students.activeCount() < 1;
    }

    /**
     * checkTonersLimit to check the papers limit.
     *
     * @return true if toner limit is up to the limit, false otherwise.
     */
    private boolean checkTonersLimit() {
        return currTonerLevel >= Minimum_Toner_Level;
    }

    /**
     * toString to give an overview of the printer.
     *
     * @return printer details.
     */
    @Override
    public String toString() {
        return "Printer Details [" +
                "id= " + id +
                ", paper level= " + currPaperLevel +
                ", toner level= " + currTonerLevel +
                ", num of documents printed= " + numOfDocumentsPrinted +
                ']';
    }
}
