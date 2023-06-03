public class PaperTechnician extends Thread{
    private ServicePrinter printer;

    public PaperTechnician(String name, ThreadGroup group, ServicePrinter printer) {
        super(group, name);
        this.printer = printer;
    }

    @Override
    public void run(){
        for(int i = 0; i<3; i++){
            ConsoleOutput.print(ConsoleOutput.DataFormat.PAPER_TECHNICIAN,"Paper Technician checks for paper-refill....", ConsoleOutput.MessageFormat.INFO);
            printer.refillPaper();

            int minSleep = 1000;
            int maxSleep = 5000;
            //Generate random number of sleep time from 1000ms to 5000ms
            int sleepTime = (int)(Math.random()*(maxSleep-minSleep+1)+ minSleep);
            try{
                sleep(sleepTime);
            }catch (InterruptedException e){
                ConsoleOutput.print(ConsoleOutput.DataFormat.PAPER_TECHNICIAN, e.toString(), ConsoleOutput.MessageFormat.ERROR);
            }
        }
        ConsoleOutput.print(ConsoleOutput.DataFormat.PAPER_TECHNICIAN,"Paper Technician Finished, packs of paper used: " + LaserPrinter.paperRefill, ConsoleOutput.MessageFormat.INFO);
    }
}
