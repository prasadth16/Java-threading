import java.util.concurrent.TimeUnit;

public class ThreadCreator implements Runnable{

    @Override
    public void run(){
        System.out.println("Hello world from run");
    }


    public static void main(String[] s){
        //way to spawn a thread in jdk 25 onwards
        Runnable r = new ThreadCreator();
        Thread.ofPlatform().name("Test-Thread").daemon(false).start(r);
        //other different ways to create thread
        //1->
        Thread t1 = new Thread(()->{
            try {
                System.out.println("Hello World, I am sleeping for 2 seconds now...");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        //t1.start();
        //2->
       Thread t2 = Thread.ofPlatform().start(
                ()->{
                    try {
                        System.out.println("This is the second way to spawn a thread.....");
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
       t1.start();

    }

}
