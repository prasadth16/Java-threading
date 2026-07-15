public class InteruptFlagDemo {
    public void run(){
        while (true){
//            if (Thread.currentThread().isInterrupted()){
//                System.out.println("Someone interupted, hence exiting immidiatly");
//                break;
//            }
            System.out.println("Mocking doing some serious work..");
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println("Someone interupted");
                //System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(Thread.interrupted());
                break;
            }
        }
    }

    public static void main(String[] args) {
        InteruptFlagDemo demo = new InteruptFlagDemo();
        Thread thread = new Thread(demo::run);
        thread.start();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
