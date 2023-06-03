public class TonerTechnician extends Thread{
    private ServicePrinter printer;

    public TonerTechnician(String name, ThreadGroup group, ServicePrinter printer) {
        super(group, name);
        this.printer = printer;
    }

    @Override
    public void run(){
        for(int i = 0; i < 3; i++){
            ConsoleOutput.print(ConsoleOutput.DataFormat.TONER_TECHNICIAN,"Toner Technician checks for cartridges replacement....", ConsoleOutput.MessageFormat.INFO);
            printer.replaceTonerCartridge();

            int minSleep = 1000;
            int maxSleep = 5000;
            //Generate random number of sleep time from 1000ms to 5000ms
            int sleepTime = (int)(Math.random()*(maxSleep-minSleep+1)+ minSleep);
            try{
                sleep(sleepTime);
            }catch (InterruptedException e){
                ConsoleOutput.print(ConsoleOutput.DataFormat.TONER_TECHNICIAN,e.toString() , ConsoleOutput.MessageFormat.ERROR);
            }
        }
        ConsoleOutput.print(ConsoleOutput.DataFormat.TONER_TECHNICIAN,"Toner Technician Finished, cartridges replaced: " + LaserPrinter.tonerRefill, ConsoleOutput.MessageFormat.INFO);
    }
}
