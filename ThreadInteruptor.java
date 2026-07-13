import java.util.concurrent.TimeUnit;

public class ThreadInteruptor {

    public static void main(String[] args) throws InterruptedException {
        Runnable workLoad = new InterruptiblePrimeFinder(100000);
        Thread primeCalculator = Thread.ofPlatform().start(workLoad); // This is started asynchronously asynchronouslyThread
//        Thread primeCalculator = new Thread(workLoad);
//        primeCalculator.start();
        System.out.println("Main thread sleeing for 1000 milliseconds");
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("Main thread woke up from sleep. Now interrupting the prime number calculator....");
        primeCalculator.interrupt();
        System.out.println("Prime calculator interrupted...it will save the work and exit..");
    }
}

class InterruptiblePrimeFinder implements Runnable{
    private int nthPrimeNumber;
    public InterruptiblePrimeFinder(int n){
        this.nthPrimeNumber = n;
    }

    @Override
   public void run() {
        try{
            long primeNumber = calculateNthPrime();
            System.out.println("Prime Number Calculated... " + primeNumber );
        }catch (InterruptedException e){
            System.out.println(Thread.currentThread().getName()+" Interrupted hence I am mocking to save my work and then exit..");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Work saved.... Exiting now...");
        }
    }
    public long calculateNthPrime() throws InterruptedException{
        System.out.println("Finding prime number #" + nthPrimeNumber);
        long prime = findNthPrimeSlowly(nthPrimeNumber);
        System.out.println("Prime number #" + nthPrimeNumber + " is " + prime);
        return prime;
    }

    /**
     * Intentionally inefficient implementation.
     */
    private static long findNthPrimeSlowly(int n)
            throws InterruptedException {

        if (n <= 0) {
            throw new IllegalArgumentException(
                    "n must be greater than zero"
            );
        }

        int primeCount = 0;
        long candidate = 2;

        while (primeCount < n) {

            checkForInterruption();

            if (isPrimeSlowly(candidate)) {
                primeCount++;

                if (primeCount % 1_000 == 0) {
                    System.out.println(
                            "Found " + primeCount
                                    + " primes. Current candidate: "
                                    + candidate
                    );
                }
            }

            candidate++;
        }

        return candidate - 1;
    }

    /**
     * Very poor prime-checking algorithm.
     *
     * It checks every number from 2 through candidate - 1.
     */
    private static boolean isPrimeSlowly(long candidate)
            throws InterruptedException {

        if (candidate < 2) {
            return false;
        }

        for (long divisor = 2; divisor < candidate; divisor++) {

            /*
             * Checking interruption inside the expensive loop is important.
             * Calling interrupt() does not forcibly stop a Java thread.
             */
            if (divisor % 10_000 == 0) {
                checkForInterruption();
            }

            if (candidate % divisor == 0) {
                return false;
            }
        }

        return true;
    }

    private static void checkForInterruption()
            throws InterruptedException {

        /*
         * Thread.interrupted() checks the flag and clears it.
         * We immediately throw InterruptedException to stop the computation.
         */
        if (Thread.interrupted()) {
            throw new InterruptedException(
                    "Prime calculation interrupted"
            );
        }
    }
}
