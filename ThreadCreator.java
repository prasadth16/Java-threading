public class ThreadCreator implements Runnable{

    @Override
    public void run(){
        System.out.println("Hello world from run");
    }

    public static void main(String[] s){
        //way to spawn a thread in jdk 25 onwards
        Runnable r = new ThreadCreator();
        Thread.ofPlatform().name("Test-Thread").daemon(false).start(r);
    }

}
